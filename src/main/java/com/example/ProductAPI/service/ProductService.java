package com.example.ProductAPI.service;

import com.example.ProductAPI.model.Product;
import com.example.ProductAPI.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Value("${userapi.uri}")
    String uri;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<Object> newProduct(Product product){
        if (verfiyUser(product.getOwner_id())){

            productRepository.save(product);
            return  new ResponseEntity<>(product, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User does not exit",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> getProduct(int id){

        var response=productRepository.findById(id);

        if (response.isPresent())
            return new ResponseEntity<>( response, HttpStatus.OK);
        return new ResponseEntity<>( "Product does not exist", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> getAllProducts(){

        var response=productRepository.findAll();



        if (!response.isEmpty())
            return new ResponseEntity<>( response, HttpStatus.OK);
        return new ResponseEntity<>( "users empty ", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Object> getProductByUserId(int userId){

        var response=productRepository.findByUserId(userId);

        if (!response.isEmpty())
            return new ResponseEntity<>( response, HttpStatus.OK);
        return new ResponseEntity<>( "Product or owner does not exist", HttpStatus.BAD_REQUEST);
    }


    private boolean verfiyUser(Integer userId){

        ResponseEntity<String> responseEntity = null;
        try {


            RestTemplate restTemplate=new RestTemplate();
            var result= restTemplate.getForEntity(uri+userId,Object.class);
            if(result.getStatusCode().value()== 200)
                return true;

        }
        catch(HttpClientErrorException e) {
            responseEntity = new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
        }catch(HttpServerErrorException e) {
            responseEntity = new ResponseEntity<>(e.getResponseBodyAsString(), HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }catch(Exception e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            throw e;
        }
        return false;
    }


}
