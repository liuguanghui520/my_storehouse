package com.nep.io;

import java.io.*;

public class FileIO{
    public static Object readObject(String filepath){
        File file = new File(filepath);
        InputStream is = null;
        ObjectInputStream ois = null;
        Object obj = null;
        try{
            is = new FileInputStream(file);
            ois = new ObjectInputStream(is);
            obj = ois.readObject();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try {
                if(ois != null){
                    ois.close();
                }
                if(is != null){
                    is.close();
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return obj ;
    }


    public static void writeObject(String fileName, Object obj){
        File file=new File(fileName);
        OutputStream out=null;
        ObjectOutputStream oos=null;

        try{
            out=new FileOutputStream(file);
            oos=new ObjectOutputStream(out);

            oos.writeObject(obj);
            oos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
                if(oos!=null){
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                if(out!=null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
