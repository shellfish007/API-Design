import grpc
from inventory_client import InventoryClient
import os
import sys
sys.path.append(os.path.dirname(__file__) + '/..')
sys.path.append(os.path.dirname(__file__) + '/../service')
from service import a3_pb2 as pb2


class GetBookTitlesClient(object):
    """
    Client for get book titles
    """

    def __init__(self, IClient):
        self.client = IClient

    def get_book_titles(self, book_list):
        """
        For each ISBN in book list, if the getBook rpc's response status is ok, add the title to the result list,
        otherwise, append a None to result list.
        """
        title_list = []
        for ISBN in book_list:
            response = self.client.getBook(ISBN)
            if response.status == 0:
                title_list.append(response.bk.title)
            else:
                title_list.append(None)
        return title_list


if __name__ == '__main__':
    client = GetBookTitlesClient(InventoryClient())
    # Get two ISBN that exists in database
    rst = client.get_book_titles(["123", "231"])
    print(rst)
