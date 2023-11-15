package com.richardduclos.tierlist.services;

import com.richardduclos.tierlist.entities.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.zip.GZIPInputStream;

@Service
public class ElementService {
    @Autowired
    DataSource dataSource;

    public Element addBlob(Element element) throws SQLException {
        var connection = dataSource.getConnection();
//        element.setImageBlob(element.getImageData());
        Blob blob = connection.createBlob();
        blob.setBytes(1, element.getImageData().getBytes());
        element.setImageBlob(blob);
        return element;
    }

    public Element retrieveBlob(Element element) throws Exception {
        Blob blob = element.getImageBlob();
        String str = new String(blob.getBytes(1l, (int) blob.length()));
        element.setImageData(str);
        element.setImageBlob(null);
        return element;
    }
}
