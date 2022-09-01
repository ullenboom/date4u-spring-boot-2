package com.tutego.date4u.productdatabase;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

interface ProductRepository extends MongoRepository<Product, ObjectId> { }