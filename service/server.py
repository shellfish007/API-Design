from concurrent import futures
import grpc
import a3_pb2 as pb2
import a3_pb2_grpc as pb2_grpc


class InventoryService(pb2_grpc.InventoryServiceServicer):
    """
    Server for gRPC functionality
    """

    def __init__(self):
        """
        Initialize the database
        """
        book1 = {"ISBN": "123", "title": "Alice in Wonderland", "author": "Alice", "genre": 1, "year": 2022}
        book2 = {"ISBN": "231", "title": "Bob in Town", "author": "Bob", "genre": 0, "year": 2012}
        book3 = {"ISBN": "312", "title": "David in Bathroom", "author": "David", "genre": 1, "year": 2000}
        self.books = {"123": book1, "231": book2, "312": book3}

    def CreateBook(self, request, context):
        """
        Server function to create a book
        """
        if self.books.__contains__(request.ISBN):
            # If the corresponding ISBN already exists, report error
            return pb2.CreateBookResponse(status=1, message="ISBN already exists!")
        book = {"ISBN": request.ISBN,
                "title": request.title,
                "author": request.author,
                "genre": request.genre,
                "year": request.year}
        # Store the book to database
        self.books[request.ISBN] = book
        response = pb2.CreateBookResponse(status=0, message="Successfully created a book!")
        print(response)
        return response

    def GetBook(self, request, context):
        """
        Server function to get a book
        """
        if not self.books.__contains__(request.ISBN):
            # If the corresponding ISBN does not exist, report error
            return pb2.GetBookResponse(status=1, msg="ISBN not exists!")
        # Fetch the book from database
        book = self.books[request.ISBN]
        response = pb2.GetBookResponse(status=0, bk=pb2.Book(
            ISBN=book['ISBN'], title=book['title'], author=book['author'],
            genre=book['genre'], year=book['year']))
        print(response)
        return response


def server():
    """
    Start a server
    """
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=2))
    pb2_grpc.add_InventoryServiceServicer_to_server(InventoryService(), server)
    server.add_insecure_port('[::]:50051')
    print("gRPC starting")
    server.start()
    server.wait_for_termination()


if __name__ == '__main__':
    server()
