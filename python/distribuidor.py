import zmq
import uuid
import random

context = zmq.Context()

# socket para fazer o push do payload para os workers
socket = context.socket(zmq.PUSH)
socket.bind("tcp://*:9000")

while True:
    """
    Vamos gerar um arquivo com 20000 linhas
    e 200 números aleatórios em cada linha
    separados por espaço.
    """
    print("generating batch...")
    with open("numbers", "w") as file:
        for _ in range(20_000):
            items = []
            for _ in range(200):
                items.append(str(random.randrange(1, 512)) + " ")
            items.append("\n")
            file.writelines(items)
   
    
    batch_id = str(uuid.uuid4())
    print(f"sending new batch: {batch_id}")
    
    with open("numbers", "r") as file:
        
        lines = file.readlines()
        num_lines = len(lines)
        
        # vamos mandar de 512 em 512 linhas
        lines_buffer = 512

        curr_line = 0
        lines_to_send = []
        batch_part = 0

        for line in lines:
            curr_line += 1
            lines_to_send.append(line)

            lines_left = num_lines - curr_line

            if len(lines_to_send) == lines_buffer or lines_left == 0:
                batch_part += 1
                socket.send_json({
                    "batch_id": batch_id,
                    "total_lines" : num_lines,
                    "lines": lines_to_send})
                lines_to_send = []
        
        print(f"all parts sent for batch {batch_id}")
        
