/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.bkland.service;

import com.api.bkland.entity.Photo;
import com.api.bkland.repository.PhotoRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 *
 * @author dieppv
 */
@Service
public class PhotoService {
    
    @Autowired
    private PhotoRepository photoRepository;

    @Transactional
    public String addPhoto(String title, MultipartFile file) throws IOException { 
        Photo photo = new Photo(title);
        photo.setImage(
          new Binary(BsonBinarySubType.BINARY, file.getBytes())); 
        photo = photoRepository.insert(photo); return photo.getId().toString();
    }

    public Photo getPhoto(String id) { 
        return photoRepository.findById(new ObjectId(id)).get();
    }

    @Transactional
    public void deletePhotoById(String id) {
        ObjectId objectId = new ObjectId(id);
        photoRepository.deleteById(objectId);
    }
}
