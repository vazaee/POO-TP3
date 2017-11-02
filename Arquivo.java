import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Arquivo {
	
	private List<String> ordena;
	private List<String> list;
	private Map<String,Integer> palArq;
	private Map<String,String> palNum;
	private Set<String> conjStops;
	private int maior;
	private int contLinha;
	private String maisFreq; 
	
	public Arquivo() {
		ordena = new ArrayList<>();
		list = new ArrayList<>();
		palNum = new HashMap<>();
		palArq = new HashMap<>();
		conjStops = new HashSet<>();
		maior = 0;
		contLinha = 0;
		maisFreq = "";
	}

	public void open(String file) {
		Path path1 = Paths.get(file);
		try (BufferedReader br = Files.newBufferedReader(path1, Charset.forName("UTF-8"))) {
	        String linha = null;
			while ((linha = br.readLine()) != null) { 	        	
				Scanner sc = new Scanner(linha).useDelimiter(" ");
	        	contLinha++;
	        	int contVezes = 0;
	        	String cont = Integer.toString(contLinha);

	        	while(sc.hasNext()) {
    				String palavra = sc.next().toLowerCase();
	        		palavra = removeAcentos(palavra);
	        		if(verificaSW(palavra)==false) {
	        			if(palArq.containsKey(palavra)) {
	        				
	        				if(contVezes==0) {
	        					linhasPalavras(palavra, cont);
	        					contVezes++;
	        				}
	        				
	        				int aux = palArq.get(palavra);
	        				aux++;
	        				palArq.put(palavra, aux);
	        			}
	        			else {
	        				palNum.put(palavra, cont);
	        				contVezes++;
	        				palArq.put(palavra, 1);
	        			}
	        			palavraMaisFrequente(palavra);
	        		}
	        	}
	        	list.add(linha);
	        }
	    }catch (IOException x) {
	        	System.out.println(x);
	    }
	}

	
	public boolean lerStopwords() {
		Path path1 = Paths.get("stopwords.txt");
	    
		try (Scanner sc = new Scanner(Files.newBufferedReader(path1, Charset.defaultCharset()))) {
	        while (sc.hasNext()) { 
	        	conjStops.add(sc.next());
	        }
	        return true;
	    }catch (IOException x) {
	        	System.out.println(x);
	        return false;
	    }
		
	}
	
	public boolean verificaSW(String p) {
		if(conjStops.contains(p)) return true;
		return false;
	}
	
	public void writingFile(String file) {
		Path path1 = Paths.get(file);
		
		try(PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path1, Charset.forName("UTF-8"))))
		{
			writer.println("Palavra mais frequente: "+maisFreq+"\n");
			writer.println();
			for(int i = 0; i<list.size();i++)
				writer.println((i+1)+" "+list.get(i).toString());
			writer.println();
			
			for(int i = 0; i<ordena.size();i++)
				writer.println(ordena.get(i).toString());
			writer.println();
			
		}
		catch (IOException e)
		{
			System.err.format("Erro de E/S: %s%n", e);
		}
	} 
	
	public void ordena() {
		Set<Map.Entry<String, String>> set = palNum.entrySet();
		for(Map.Entry<String, String> me: set) {
			ordena.add(me.getKey()+": "+me.getValue());
		}
		Collections.sort(ordena);
	}
	
	public String removeAcentos(String str) {
		  str = str.replaceAll("[^a-zZ-Z1-9 //']", "");
		  str = str.replace("[www,org]", "");
		  return str;
	}
	
	public void linhasPalavras(String p, String c) {
		String aux = "";
		if(palNum.get(p)!=null)
			aux = palNum.get(p) + ", " +c;
		palNum.put(p,aux);
	}
	
	public void palavraMaisFrequente(String p) {
		if(palArq.get(p)>maior) {
			maior = palArq.get(p);
			maisFreq = p;
		}		
	}
	
	
	public String toString() {
		String s = "";
		s = "Palavra mais frequente: "+maisFreq+"\n";
		for(int i = 0; i<list.size();i++)
			s = s + "\n"+(i+1)+" "+list.get(i).toString();
		s = s+"\n";
		
		for(int i = 0; i<ordena.size();i++)
			s = s + "\n" + ordena.get(i).toString();
		return s;
	}
}
