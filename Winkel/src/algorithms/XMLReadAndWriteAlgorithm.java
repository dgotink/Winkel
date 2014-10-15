package algorithms;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import db.DbException;
import db.IReaderAndWriter;
import domain.Item;
import domain.ItemType;
import domain.Shop;

public class XMLReadAndWriteAlgorithm implements IReaderAndWriter
{
	
	public XMLReadAndWriteAlgorithm() throws DbException, IOException 
	{
	}

	public void writeItem(File file, Shop shop) throws DbException
	{
		    // Create a XMLOutputFactory
		    XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		    // Create XMLEventWriter
		    XMLEventWriter eventWriter;
			try {
				eventWriter = outputFactory
				    .createXMLEventWriter(new FileOutputStream(file.getName()));
			} catch (FileNotFoundException | XMLStreamException e1) {
				throw new DbException("wrong file");
			}
		    // Create a EventFactory
		    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		    XMLEvent end = eventFactory.createDTD("\n");
		    // Create and write Start Tag
		    StartDocument startDocument = eventFactory.createStartDocument();
		    try{
		    eventWriter.add(startDocument);
		    eventWriter.add(end);
		    StartElement productenStartElement = eventFactory.createStartElement("","",XmlTags.PRODUCTEN);
		    eventWriter.add(productenStartElement);
	    	eventWriter.add(end);
		    // Create product open tag
		    Collection<Item> producten = shop.getValueList();
		    
		    for(Item item: producten)
		    {
	  	    	eventWriter.add(eventFactory.createStartElement("","",XmlTags.PRODUCT));
		    	eventWriter.add(end);
		    	// Write the different nodes
		    	createNode(eventWriter, XmlTags.TYPE, item.getType().toString());
		    	createNode(eventWriter, XmlTags.ID, shop.getKey(item));
		    	createNode(eventWriter, XmlTags.TITLE, item.getTitle());
		    	eventWriter.add(eventFactory.createEndElement("", "",XmlTags.PRODUCT));
		    	eventWriter.add(end);
		    }
		    eventWriter.add(eventFactory.createEndElement("", "",XmlTags.PRODUCTEN));
		    eventWriter.add(eventFactory.createEndDocument());
		    eventWriter.close();
		    } catch(XMLStreamException e){
		    	throw new DbException("wrong format");
		    }
		}
		
		private void createNode(XMLEventWriter eventWriter, String name, String value) 
			throws XMLStreamException {
		    XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		    XMLEvent end = eventFactory.createDTD("\n");
		    XMLEvent tab = eventFactory.createDTD("\t");
		    // Create Start node
		    StartElement sElement = eventFactory.createStartElement("", "", name);
		    eventWriter.add(tab);
		    eventWriter.add(sElement);
		    // Create Content
		    Characters characters = eventFactory.createCharacters(value);
		    eventWriter.add(characters);
		    // Create End node
		    EndElement eElement = eventFactory.createEndElement("", "", name);
		    eventWriter.add(eElement);
		    eventWriter.add(end);
		}
		
		public Shop readItem(File file, Shop shop) throws DbException
		{
			try {
				// First create a new XMLInputFactory
			    XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			    // Setup a new eventReader
			    InputStream in = new FileInputStream(file.getName());
			    XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			    // Read the XML document
			    String type = null;
		        String id = null;
		        String title = null;
		            
			    while (eventReader.hasNext()) {
			    	XMLEvent event = eventReader.nextEvent();
			        if (event.isStartElement()) {
			        	StartElement sE = event.asStartElement();
			        	// If we have a item element we create a new item
			        	if (sE.getName().getLocalPart() == (XmlTags.PRODUCT)) {
			        		
			        	}
			        	else if (sE.getName().getLocalPart().equals(XmlTags.TYPE)) {
				            event = eventReader.nextEvent();
				            type = event.asCharacters().getData();
				        }
			        	else if (sE.getName().getLocalPart().equals(XmlTags.ID)) {
				            event = eventReader.nextEvent();
				            id = event.asCharacters().getData();
				        }
			        	else if (sE.getName().getLocalPart().equals(XmlTags.TITLE)) {
				            event = eventReader.nextEvent();
				            title = event.asCharacters().getData();
				        }
			        }		       
			        // If we reach the end of an item element we add it to the list
			        else if (event.isEndElement()) {
			          EndElement endElement = event.asEndElement();
			          if (endElement.getName().getLocalPart() == (XmlTags.PRODUCT)) {
			            try {
			            	shop.addItem(ItemType.valueOf(type.toUpperCase()), id, title);
						} catch (Exception e) {
							throw new DbException("product from file cannot be added");
						}
			          }
			        }
			      }
			    } catch (FileNotFoundException e) {
			    	throw new DbException("file not found");
			    } catch (XMLStreamException e) {
			    	throw new DbException("file wrong format");
			    }
			return shop;
		}

		@Override
		public void writeCustomer(File file, Shop shop) throws DbException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Shop readCustomer(File file, Shop shop) throws DbException {
			// TODO Auto-generated method stub
			return null;
		}
}
