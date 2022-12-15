from get_book_titles import GetBookTitlesClient
from inventory_client import InventoryClient
from unittest.mock import MagicMock
import os
import sys

sys.path.append(os.path.dirname(__file__) + '/..')
sys.path.append(os.path.dirname(__file__) + '/../service')
from service import a3_pb2 as pb2
from service import a3_pb2_grpc as pb2_grpc

book1 = {"ISBN": "123", "title": "Alice in Wonderland", "author": "Alice", "genre": 1, "year": 2022}
book2 = {"ISBN": "231", "title": "Bob in Town", "author": "Bob", "genre": 0, "year": 2012}
book3 = {"ISBN": "312", "title": "David in Bathroom", "author": "David", "genre": 1, "year": 2000}
books = {"123": book1, "231": book2, "312": book3}


def side_effect(arg):
    if not books.__contains__(arg):
        # If the corresponding ISBN does not exist, report error
        return pb2.GetBookResponse(status=1, msg="ISBN not exists!")
    # Fetch the book from database
    book = books[arg]
    response = pb2.GetBookResponse(status=0, bk=pb2.Book(
        ISBN=book['ISBN'], title=book['title'], author=book['author'],
        genre=book['genre'], year=book['year']))
    return response


class Tests(object):

    def __init__(self, IClient):
        self.getbookClient = GetBookTitlesClient(IClient)

    def test1(self):
        response = self.getbookClient.get_book_titles(["123", "231"])
        assert (response == ['Alice in Wonderland', 'Bob in Town'])

    def test2(self):
        response = self.getbookClient.get_book_titles(["123", "3"])
        assert (response == ['Alice in Wonderland', None])

    def test3(self):
        response = self.getbookClient.get_book_titles(["13", "231"])
        assert (response == [None, 'Bob in Town'])

    def test4(self):
        response = self.getbookClient.get_book_titles(["13", "2"])
        assert (response == [None, None])

    def test5(self):
        response = self.getbookClient.get_book_titles(["312", "123", "231"])
        assert (response == ['David in Bathroom', 'Alice in Wonderland', 'Bob in Town'])

    def runTests(self):
        self.test1()
        self.test2()
        self.test3()
        self.test4()
        self.test5()


if __name__ == '__main__':
    # run mock server
    mockInventoryClient = MagicMock(InventoryClient)
    mockInventoryClient.getBook.side_effect = side_effect
    mockTest = Tests(mockInventoryClient)
    mockTest.runTests()
    print("Pass all mock tests!")

    # run real server
    liveInventoryClient = InventoryClient()
    liveTest = Tests(liveInventoryClient)
    liveTest.runTests()
    print("Pass all live tests!")
