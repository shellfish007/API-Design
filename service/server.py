from concurrent import futures
import grpc
import a3_pb2 as pb2
import a3_pb2_grpc as pb2_grpc


class InventoryService(pb2_grpc.InventoryServiceServicer):
    def __init__(self):
        book1 = {"ISBN": "123", "title": "Alice in Wonderland", "author": "Alice", "genre": 1, "year": 2022}
        book2 = {"ISBN": "231", "title": "Bob in Town", "author": "Bob", "genre": 0, "year": 2012}
        book3 = {"ISBN": "312", "title": "David in Bathroom", "author": "David", "genre": 1, "year": 2000}
        self.books = {"123": book1, "231": book2, "312": book3}

    def CreateBook(self, request, context):
        if self.books.__contains__(request.ISBN):
            return pb2.CreateBookResponse(status=1, message="ISBN already exists!")
        book = {"ISBN": request.ISBN,
                "title": request.title,
                "author": request.author,
                "genre": request.genre,
                "year": request.year}
        self.books[request.ISBN] = book
        response = pb2.CreateBookResponse(status=0, message="Successfully created a book!")
        print(response)
        return response

    def GetBook(self, request, context):
        if not self.books.__contains__(request.ISBN):
            return pb2.GetBookResponse(status=1, msg="ISBN not exists!")
        book = self.books[request.ISBN]
        response = pb2.GetBookResponse(status=0, bk=pb2.Book(
            ISBN=book['ISBN'], title=book['title'], author=book['author'],
            genre=book['genre'], year=book['year']))
        print(response)
        return response


def server():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=2))
    pb2_grpc.add_InventoryServiceServicer_to_server(InventoryService(), server)
    server.add_insecure_port('[::]:50051')
    print("gRPC starting")
    server.start()
    server.wait_for_termination()


if __name__ == '__main__':
    server()
