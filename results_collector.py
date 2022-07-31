import time
import zmq
import random


context = zmq.Context()

# socket para receber os resultados
socket = context.socket(zmq.PULL)
socket.bind("tcp://*:9001")

batches = {}

while True:
    payload = socket.recv_json()
    batch_id = payload["batch_id"]
    if batch_id not in batches.keys():
        batches[batch_id] = {
                "batch_id": batch_id,
                "total_lines" : payload["total_lines"],
                "lines_processed" : 0,
                "results": []
            }

    batch = batches[batch_id]

    batch["lines_processed"] = payload["lines_processed"] + batch["lines_processed"]
    batch["results"].append(payload)
    total_lines = batch["total_lines"]
    
    if (batch["lines_processed"] == total_lines):
        partial_sums = [result["sum"] for result in batch["results"]]
        batch_sum = sum(partial_sums)
        print(f"batch {batch_id} result: {batch_sum}")
