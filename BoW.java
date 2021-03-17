/*
 BoW is an approach used in semantic similarity.
 The idea is quite simple,
 make a vector of every document in the used corpus
 the vector has dim=number_of_different_words_in_the_corpus
 every document is identified as a vector which contains 0 in every position of words that 
 do not appear in it and a number indicating the specificity of every word in it
 This is an immature trial.
 */
package bow;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author kalliopi meladaki
 */
public class BoW {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        // TODO code application logic here
        //why Hashmap? Hashmap allows duplicate values whether hashset not
        //https://beginnersbook.com/2014/08/hashset-vs-hashmap-java/
        
        
        //variables initialization
         Map<String, Integer> word_count=new LinkedHashMap<String, Integer>();
         Vector vec =new Vector();
         Map<String, Vector> doc_vecs=new LinkedHashMap<String, Vector>(1);
         int vector_length=0;
         
        //enter first file
        System.out.println("Enter path of .txt files");
        Scanner scanner=new Scanner(System.in);
        String path=scanner.next();
        
        
        //Reading the files loop https://stackoverflow.com/questions/5924237/java-read-all-txt-files-in-folder
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        

        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];    
             if (file.isFile() && file.getName().endsWith(".txt")) {
                 
                //read file, pdf p. 472
                try{
                    FileReader filereader=new FileReader(file);
                    BufferedReader reader=new BufferedReader(filereader);
                    String line=null;
                
                    int new_value;//this variable is usefull for value reset in hashmap
                
                    while ((line=reader.readLine())!=null){ 
                        System.out.println(line);
                        String[] result=line.split(" ");//split sentences to words pdf p. 
                    
                    
                        //place words in hashmap Word_count
                        for ( i=0;i<result.length;i++){
                            result[i]=result[i].replaceAll("\\W", "");//get rid of punctuation!!
                        
                            //count how many times a word appeared in a doc
                            if (word_count.get(result[i])==null){
                                word_count.put(result[i],1);
                            }
                            else{
                                new_value= word_count.get(result[i])+1;
                                word_count.put(result[i],new_value);
                            }
                        }
                    }
                    reader.close();//close doc_file
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                //put hashmap values to vector
                vec.addAll(word_count.values());
                vector_length=vec.size();
                System.out.println(vec);
            
                // print final result of doc's hashmap
                System.out.println(word_count);
            
                //make a vector for each doc
                doc_vecs.put(file.getName(),vec);
            
                //print vector of a doc
                System.out.println(doc_vecs);
            
                //remove every vector value
                //vec.clear();
                vec=new Vector();
                System.out.println("Now new vec "+vec);
            
                //clear hashmaps values not keys
                for (String key : word_count.keySet()){
                    word_count.put(key,0);
                }
                System.out.println("now the hash map "+word_count);
            }//end of if for files loop
        }//end of reading files loop
        
        //at this point our hashmap of doc_vecs is complete, but vectors don't have the same length-dimention 
        //all we need to do is add some 0 at the end of the short ones
        int vecs_size=0;
        for (String key : doc_vecs.keySet()){
            vecs_size=doc_vecs.get(key).size();
            if (vecs_size<vector_length){
                for (int i=vecs_size;i<vector_length;i++){
                    doc_vecs.get(key).add(0);
                }   
            }
         }
        
        
    }//end of main
    
}//end of class
