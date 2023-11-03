package org.main;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Queue;

import org.jdom2.Document;
import org.jdom2.Element;

public class Serializer
{
    public Document serialize(Object obj)
    {
        Element		root		= new Element("serialized");
        Document	document 	= new Document(root);
        if (obj != null)
        {
        	IdentityHashMap<Object, Integer> 	identityHashMap = new IdentityHashMap<>();
        	Queue<Object>						queue 			= new ArrayDeque<>();
        	queue.add(obj);
        	serializeObject(root, queue, identityHashMap);
        }
        // If object is null, just return the empty root object.
        return document;
    }
    
    private void serializeObject(Element root, Queue<Object> queue, IdentityHashMap<Object, Integer> identityHashMap)
    {
        if (!queue.isEmpty())
        {
            Object 	obj = queue.remove();
            int		id;
            
            // Get object id
            if (!identityHashMap.containsKey(obj))
            {
            	id = identityHashMap.size();
                identityHashMap.put(obj, id);
            }
            else
            	id = identityHashMap.get(obj);
            
            Element objectElement = getObjectElement(obj.getClass().getName(), String.valueOf(id));
            root.addContent(objectElement);

            if (obj.getClass().isArray() || obj instanceof Collection)
            {
            	Object	array	= obj.getClass().isArray() ? obj : ((Collection<?>) obj).toArray();
            	int		length	= Array.getLength(array);
            	
            	if (obj.getClass().isArray())
    				objectElement.setAttribute("length", String.valueOf(length));
				  
				for (int i = 0; i < length; i++)
					processObject(Array.get(array, i), objectElement, queue, identityHashMap);
            }
            else
            {
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields)
                {
                    field.setAccessible(true);                    
                    Element fieldElement = getFieldElement(field.getName(), field.getDeclaringClass().getName());              
                    try
                    {
                        if (processObject(field.get(obj), fieldElement, queue, identityHashMap))
                        	objectElement.addContent(fieldElement);
                    }
                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            serializeObject(root, queue, identityHashMap);
        }
    }
    
    private boolean isPrimitive(Class<?> type)
    {
    	return type.isPrimitive()
    			|| type == Boolean.class 
    			|| type == Character.class 
    			|| type == Byte.class 
    			|| type == Short.class 
    			|| type == Integer.class 
    			|| type == Long.class 
    			|| type == Float.class 
    			|| type == Double.class;
    }
    
    private int getObjectId(Object obj, IdentityHashMap<Object, Integer> identityHashMap)
    {
    	return identityHashMap.containsKey(obj)
    			? identityHashMap.get(obj) 
    			: identityHashMap.size();
    }
    
    private Element getValueElement(String value)
    {
    	Element valueElement = new Element("value");
        valueElement.setText(value);
        return valueElement;
    }
    
    private Element getReferenceElement(String refId)
    {
    	Element referenceElement = new Element("reference");
		referenceElement.setText(String.valueOf(refId));
		return referenceElement;
    }
    
    private Element getFieldElement(String name, String declaringClass) 
    {
    	Element fieldElement = new Element("field");
        fieldElement.setAttribute("name", name);
        fieldElement.setAttribute("declaringclass", declaringClass);
        return fieldElement;
    }
    
    private Element getObjectElement(String className, String id)
    {
    	Element objectElement = new Element("object");
        objectElement.setAttribute("class", className);
        objectElement.setAttribute("id", id);
        return objectElement;
    }
    
    private boolean processObject(Object object, Element parent, Queue<Object> queue, 
    		IdentityHashMap<Object, Integer> identityHashMap)
    {
    	if (object != null)
	    {
	        if (isPrimitive(object.getClass()))
	            parent.addContent(getValueElement(object.toString()));
	        else
	        {
	        	int		fieldObjectId		= getObjectId(object, identityHashMap);                            	
				Element referenceElement 	= getReferenceElement(String.valueOf(fieldObjectId));
				parent.addContent(referenceElement);
				
	            if (!identityHashMap.containsKey(object) && 
	            	!queue.contains(object))
	            {
	                queue.add(object);
	                identityHashMap.put(object, fieldObjectId);
	            }
	        }
	        return true;
	    }
    	return false;
    }
}