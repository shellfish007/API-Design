import grpc

import a3_pb2 as pb2
import a3_pb2_grpc as pb2_grpc


class Client(object):
    """
    Client for gRPC functionality
    """

    def __init__(self):
        self.host = 'localhost'
        self.server_port = 50051

        # instantiate a channel
        self.channel = grpc.insecure_channel(
            '{}:{}'.format(self.host, self.server_port))

        # bind the client and the server
        self.stub = pb2_grpc.InventoryServiceStub(self.channel)

    def getBook(self, ISBN):
        """
        Client function to call the rpc for GetServerResponse
        """
        response = self.stub.GetBook(pb2.Identifier(ISBN=ISBN))
        print(response)

    def createBook(self, book):
        pb2_book = pb2.Book(
                ISBN=book['ISBN'],
                title=book['title'],
                author=book['author'],
                genre=book['genre'],
                year=book['year']
        )
        self.stub.CreateBook(pb2_book)


if __name__ == '__main__':
    client = Client()
    new_book = {"ISBN": "1234", "title": "Safe and Sound", "author": "Taylor Swift", "genre": 0, "year": 2010}
    client.createBook(new_book)
    client.getBook("123")

