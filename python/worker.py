import zmq

context = zmq.Context()

# socket para fazer o pull do payload
sender_socket = context.socket(zmq.PULL)
sender_socket.connect("tcp://127.0.0.1:9000")

# socket para fazer o push do resultado
results_socket = context.socket(zmq.PUSH)
results_socket.connect("tcp://127.0.0.1:9001")

while True:
    payload = sender_socket.recv_json()
    lines = payload.pop("lines")
    batch_id = payload["batch_id"]
    total_lines = payload["total_lines"]
    numbers = []
    for line in lines:
        line_numbers = [int(n) for n in line.split()]
        for number in line_numbers:
            numbers.append(number)
    payload["sum"] = sum(numbers)
    payload["lines_processed"] = len(lines)
    results_socket.send_json(payload)
    print(f'returned: {payload}')
