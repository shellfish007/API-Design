# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: a3.proto
"""Generated protocol buffer code."""
from google.protobuf.internal import builder as _builder
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x08\x61\x33.proto\"\x8a\x01\n\x04\x42ook\x12\x0c\n\x04ISBN\x18\x01 \x01(\t\x12\r\n\x05title\x18\x02 \x01(\t\x12\x0e\n\x06\x61uthor\x18\x03 \x01(\t\x12 \n\x05genre\x18\x04 \x01(\x0e\x32\x11.Book.genre_types\x12\x0c\n\x04year\x18\x05 \x01(\x05\"%\n\x0bgenre_types\x12\x0b\n\x07\x66iction\x10\x00\x12\t\n\x05novel\x10\x01\"`\n\rInventoryItem\x12\x0e\n\x06number\x18\x01 \x01(\x05\x12\x13\n\x02\x62k\x18\x02 \x01(\x0b\x32\x05.BookH\x00\"\"\n\x06status\x12\r\n\tavailable\x10\x00\x12\t\n\x05taken\x10\x01\x42\x06\n\x04\x62ook\"\x1a\n\nIdentifier\x12\x0c\n\x04ISBN\x18\x01 \x01(\t\"\x07\n\x05\x45mpty2R\n\x10InventoryService\x12\x1d\n\nCreateBook\x12\x05.Book\x1a\x06.Empty\"\x00\x12\x1f\n\x07GetBook\x12\x0b.Identifier\x1a\x05.Book\"\x00\x62\x06proto3')

_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, globals())
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'a3_pb2', globals())
if _descriptor._USE_C_DESCRIPTORS == False:

  DESCRIPTOR._options = None
  _BOOK._serialized_start=13
  _BOOK._serialized_end=151
  _BOOK_GENRE_TYPES._serialized_start=114
  _BOOK_GENRE_TYPES._serialized_end=151
  _INVENTORYITEM._serialized_start=153
  _INVENTORYITEM._serialized_end=249
  _INVENTORYITEM_STATUS._serialized_start=207
  _INVENTORYITEM_STATUS._serialized_end=241
  _IDENTIFIER._serialized_start=251
  _IDENTIFIER._serialized_end=277
  _EMPTY._serialized_start=279
  _EMPTY._serialized_end=286
  _INVENTORYSERVICE._serialized_start=288
  _INVENTORYSERVICE._serialized_end=370
# @@protoc_insertion_point(module_scope)