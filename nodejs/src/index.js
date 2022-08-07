import cluster from "node:cluster";
import { createReadStream } from "node:fs";
import { cpus } from "node:os";
import { createInterface } from "node:readline";
import process from "node:process";
import Queue from "bull";
import path from "node:path";

const queue = new Queue("file sum");

function primaryRoutine() {
  console.log("Worker primário no processo:", process.pid);
  const processesCount = cpus().length - 1;
  let sum = 0,
    linesProcessed = 0,
    linesRead = 0;
  for (let i = 0; i < processesCount; i++) {
    const newWorker = cluster.fork();
    console.log("Novo worker no processo:", newWorker.process.pid);
  }
  const stream = createReadStream(path.resolve() + "/../numbers");
  const readline = createInterface(stream);

  readline.on("line", (data) => {
    linesRead++;
    queue.add({ line: data });
  });

  cluster.on("message", async (_, resultFromWorker) => {
    sum += resultFromWorker;
    linesProcessed++;
  });

  async function checkIfHasEnded() {
    const { waiting, active } = await queue.getJobCounts();
    const equalsZero = (num) => num === 0;
    if ([waiting, active].every(equalsZero) && linesProcessed === linesRead) {
      console.log("Soma total:", sum);
      console.log("Quantidade de linhas processadas:", linesProcessed);
      process.exit();
    }
  }
  setInterval(checkIfHasEnded, 100);
}

function workerRoutine() {
  queue.process(function (job) {
    const result = job.data.line
      .split(" ")
      .reduce((a, b) => Number(a) + Number(b));
    process.send(result);
  });
}

cluster.isPrimary ? primaryRoutine() : workerRoutine();
