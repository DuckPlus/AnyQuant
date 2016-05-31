package DAO.impl;

import DAO.BaseDAO;
import entity.StockEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ss
 * @date 16/5/12
 */
@Repository
public class DBHelper {
    @Autowired
    BaseDAO baseDAO;

    public  void modifyDBStock(){

        ArrayList<String> result =
                (ArrayList<String>) readIndus_Loca();
        HashMap<String ,String> map = new HashMap<>();
        ArrayList<StockEntity> entities;
        if(result!=null){

            for(String line : result){

                String[] temp = line.split(",");
                map.put(temp[0],temp[1]);
                System.out.println(temp[0]+"  "+ temp[1]);
            }
            System.out.println("----------------------------------");

            if(baseDAO==null){
                System.out.println("null");
            }else{
                entities= (ArrayList<StockEntity>)
                        baseDAO.getAllList(StockEntity.class);


                for(StockEntity entity: entities){


                    entity.setBoard(map.get(entity.getCode())  );
                    System.out.println(entity.getCode()+" "+entity.getBoard());
                    baseDAO.update(entity);

                }
            }



        }else{
            System.out.println("null");
        }
    }

     private List<String> readIndus_Loca (){
         try {
             String encoding = "utf-8";
             String filePath  = "industry_loation.txt";
             File file = new File(filePath);
             if (file.isFile() && file.exists()) {
                 InputStreamReader read = new InputStreamReader
                         (new FileInputStream(file), encoding);
                 BufferedReader bufferedReader = new BufferedReader(read);
                 String temp;
                 List<String> content = new ArrayList();
                 while ((temp = bufferedReader.readLine()) != null) {
                     content.add(temp);
                 }
                 read.close();

                 return content;

             } else {
                 System.out.println("cant find" + filePath);
                 return null;
             }
         }catch(Exception e){
             e.printStackTrace();
         }
         return null;
     }



}
