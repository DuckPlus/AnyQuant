package businessLogicHelper;


import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

/** 
 * VOPO互换类
 * @author ymc，czq 
 * @version 创建时间：2015年11月18日 下午7:39:11 
 *
 */
public class VOPOchange {
	
	@SuppressWarnings("unchecked")
	public static Object POtoVO(Object o){
		
		if(o == null){
			return null;
		}
		
		
		Object vo = null;
		
		Class<? extends Object> poClass = o.getClass();
		
		Class< ? extends Object> voClass = null;
		String poName = poClass.getName();
		
		String voName = "vo"+poName.substring(2,poName.length()-2)+"VO";
		
		try {
			voClass = Class.forName(voName);
			vo = voClass.newInstance();
		} catch (Exception e) {
			System.err.println("VOPO名字不对应！！！ OR  VO不存在空的构造器");
			e.printStackTrace();
		}
		
		Field[] fields = poClass.getDeclaredFields();
		
				
		for(Field f : fields){
			Field tmp = null;
			if(f.getName().equals("serialVersionUID"))
				continue;

				
			if(f.getType().toString().endsWith("ArrayList")&&!f.getGenericType().toString().endsWith("String>")){
				
				Type listType = f.getGenericType();
				Object list = null;
				f.setAccessible(true);
				try {
					 list = f.get(o);
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(list ==null)
					continue;
				ArrayList<Object> polist = (ArrayList<Object>)list;
				
				String[] spl = listType.toString().split("<");		
				String votName = "vo"+spl[1].substring(2, spl[1].length()-3)+"VO";

				Class<? extends Object> votmp = null;

				try {
					votmp = Class.forName(votName);

				} catch (ClassNotFoundException e) {
			
					e.printStackTrace();
				}
				ArrayList<Object> volist = new ArrayList<Object>(polist.size());
				for(int j =0 ; j< polist.size(); j++){
					volist.add(votmp.cast(POtoVO(polist.get(j))));
				}
				
				Field ft = null;
				try {
					ft = vo.getClass().getDeclaredField(f.getName());
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ft.setAccessible(true);
				try {
					ft.set(vo, volist);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			else{
				try {
					tmp = voClass.getDeclaredField(f.getName());
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				f.setAccessible(true);
				tmp.setAccessible(true);
				try {
					tmp.set(vo, f.get(o));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return vo;
		
	}
	@SuppressWarnings("unchecked")
	public static Object VOtoPO(Object o){
		
		Object po = null;
		
		Class<? extends Object> voClass = o.getClass();
		
		Class<? extends Object> poClass = null;
		
		String voName = voClass.getName();
		
		String poName = "po"+voName.substring(2,voName.length()-2)+"PO";
		
		Field[] field = voClass.getDeclaredFields();
	
//		Method met = null;
		
			
		try {		
			poClass = Class.forName(poName);
			
			po = poClass.newInstance();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(voClass.getSuperclass().toString().endsWith("DocVO")){
			setSuperField(po, o, "ID");
			setSuperField(po, o, "type");
			setSuperField(po, o, "date");
			setSuperField(po, o, "state");
		}
		
		if(voClass.getSuperclass().toString().endsWith("CostVO")){
			setSuperField(po, o, "ID");
			setSuperField(po, o, "startDate");
			setSuperField(po, o, "endDate");
			setSuperField(po, o, "money");
			setSuperField(po, o, "costType");
			
		}
		for(int i= 0 ; i<field.length;i++){
			if(field[i].getType().toString().endsWith("ArrayList")&&!field[i].getGenericType().toString().endsWith("String>")){
				
				Type listType = field[i].getGenericType();
				Object list = null;
				
				try {
					 list = field[i].get(o);
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ArrayList<Object> volist = (ArrayList<Object>)list;
				
				String[] spl = listType.toString().split("<");		
				String potName = "po"+spl[1].substring(2, spl[1].length()-3)+"PO";

				Class<? extends Object> potmp = null;

				try {
					potmp = Class.forName(potName);

				} catch (ClassNotFoundException e) {
			
					e.printStackTrace();
				}
				ArrayList<Object> polist = new ArrayList<Object>(volist.size());
				for(int j =0 ; j< volist.size(); j++){
					polist.add(potmp.cast(VOtoPO(volist.get(j))));
				}
				
				Field ft = null;
				try {
					ft = po.getClass().getDeclaredField(field[i].getName());
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ft.setAccessible(true);
				try {
					ft.set(po, polist);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			else
				setSuperField(po, o, field[i].getName());
		}
		
		//System.out.println(poName);
		return po;
		
	}
	 
	private static void setSuperField(Object po,Object o, String name) {
		
		Field field1 = getSuperField(o.getClass(), name);
		Field field2 = getSuperField(po.getClass(), name);
		
		try {
			field1.setAccessible(true);
			Object val = field1.get(o);
//			System.out.println(val.toString());
//			System.out.println("ready to change "+field2.getName());
			field2.setAccessible(true);
			field2.set(po, val);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	private static Field getSuperField(Class clazz, String name) {
		
		Field[] field = clazz.getDeclaredFields();
		
		for(Field f:field ){
			if(f.getName().equals(name)){
				return f;
				
			}
		}
		
		Class supClass = clazz.getSuperclass();
		//System.out.println(supClass);
		if(supClass!=null){
			return getSuperField(supClass, name);
		}
		return null;
	}
	
}
