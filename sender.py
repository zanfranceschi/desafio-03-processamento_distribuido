import zmq
import uuid
import random

context = zmq.Context()
socket = context.socket(zmq.PUSH)
socket.bind("tcp://*:9000")

while True:
    """
    Vamos gerar um arquivo com 20000 linhas
    e 200 números aleatórios em cada linha
    separados por espaço.
    """
    with open("numbers", "w") as file:
        for _ in range(20000):
            items = []
            for _ in range(200):
                items.append(str(random.randrange(1, 512)) + " ")
            items.append("\n")
            file.writelines(items)

    
    batch_id = str(uuid.uuid4())
    with open("numbers", "r") as file:
        
        lines = file.readlines()
        num_lines = len(lines)
        
        # vamos mandar de X em X linhas
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
                    "batch_part" : batch_part,
                    "total_lines" : num_lines,
                    "lines": lines_to_send})
                lines_to_send = []
        

socket.close()
