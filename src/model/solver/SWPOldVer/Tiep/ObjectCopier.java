package model.solver.SWPOldVer.Tiep;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectCopier {
	private ObjectCopier(){}
	public static Object copy(Object obj){
		if(obj == null) return null;
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			
			byte [] arrays = baos.toByteArray();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(arrays);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		}catch(Exception e){
			throw new RuntimeException("Error when copying object");
		}
	}
}