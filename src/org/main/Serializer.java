package org.main;

import org.jdom2.Document;
import org.jdom2.Element;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Queue;

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
            
            if (!identityHashMap.containsKey(obj))
            {
            	id = identityHashMap.size();
                identityHashMap.put(obj, id);
            }
            else
            {
            	id = identityHashMap.get(obj);
            }
            
            Element objectElement 	= new Element("object");
            objectElement.setAttribute("class", obj.getClass().getSimpleName());
            objectElement.setAttribute("id", String.valueOf(id));
            root.addContent(objectElement);

            if (obj.getClass().isArray() || obj instanceof Collection)
            {
            	Object array;
            	if (obj.getClass().isArray())
            	{
            		array = obj;
            	}
            	else
            	{
            		array = ((Collection<?>) obj).toArray();
            	}
            	int length = Array.getLength(array);
				objectElement.setAttribute("length", String.valueOf(length));
				  
				for (int i = 0; i < length; i++)
				{
					Object arrayElement = Array.get(array, i);
					if (arrayElement != null)
					{
						if (isPrimitive(arrayElement.getClass()))
						{
							Element valueElement = new Element("value");
							valueElement.setText(arrayElement.toString());
							objectElement.addContent(valueElement);
						}
						else
						{
							int arrayElementId = 
									identityHashMap.containsKey(arrayElement)
	                    			? identityHashMap.get(arrayElement) 
	                    			: identityHashMap.size();
							
							Element referenceElement = new Element("reference");
							referenceElement.setText(String.valueOf(arrayElementId));
							objectElement.addContent(referenceElement);
							
							if (!identityHashMap.containsKey(arrayElement) && 
                            	!queue.contains(arrayElement))
                            {
                                queue.add(arrayElement);
                                identityHashMap.put(arrayElement, arrayElementId);
                            }
						}
					}
				}
            }
            else
            {
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields)
                {
                    field.setAccessible(true);
                    
                    Element fieldElement = new Element("field");
                    fieldElement.setAttribute("name", field.getName());
                    fieldElement.setAttribute("declaringclass", field.getDeclaringClass().getSimpleName());                
                    
                    try
                    {
                        Object fieldValue = field.get(obj);
                        if (fieldValue != null)
                        {
                            if (isPrimitive(field.getType()))
                            {
                                Element valueElement = new Element("value");
                                valueElement.setText(fieldValue.toString());
                                fieldElement.addContent(valueElement);
                            }
                            else
                            {
                            	int fieldObjectId = 
                            			identityHashMap.containsKey(fieldValue)
                            			? identityHashMap.get(fieldValue) 
                            			: identityHashMap.size();
                            	
    							Element referenceElement = new Element("reference");
    							referenceElement.setText(String.valueOf(fieldObjectId));
    							fieldElement.addContent(referenceElement);
    							
                                if (!identityHashMap.containsKey(fieldValue) && 
                                	!queue.contains(fieldValue))
                                {
                                    queue.add(fieldValue);
                                    identityHashMap.put(fieldValue, fieldObjectId);
                                }
                            }
                        }
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
}

