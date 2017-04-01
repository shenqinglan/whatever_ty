package com.whty.tls.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;

public class IniFile
{
  Hashtable<String, Hashtable<String, String>> sections;

  public IniFile()
  {
    this.sections = new Hashtable();
  }

  public IniFile(String filename)
    throws FileNotFoundException
  {
    this();
    load(filename);
  }

  public IniFile(InputStream input)
    throws IOException
  {
    this();
    load(input);
  }

  public void setKeyValue(String section, String key, String value)
  {
    try
    {
      getSection(section).put(key.toLowerCase(), value);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }

  public Hashtable<String, Hashtable<String, String>> getSections()
  {
    return this.sections;
  }

  public Hashtable<String, String> getSection(String section)
  {
    return this.sections.get(section.toLowerCase());
  }

  public boolean isNullOrEmpty(String section, String key)
  {
    String value = getKeyValue(section, key);
    return (value == null) || (value.length() == 0);
  }

  public String getKeyValue(String section, String key)
  {
    try
    {
      return getSection(section).get(key.toLowerCase()); } catch (NullPointerException e) {
    }
    return null;
  }

  public int getKeyIntValue(String section, String key)
  {
    return getKeyIntValue(section, key, 0);
  }

  public int getKeyIntValue(String section, String key, int defaultValue)
  {
    String value = getKeyValue(section, key.toLowerCase());
    if (value == null)
      return defaultValue;
    try
    {
      return Integer.parseInt(value); } catch (NumberFormatException e) {
    }
    return 0;
  }

  public String[][] getKeysAndValues(String aSection)
  {
    Hashtable section = getSection(aSection);
    if (section == null) {
      return null;
    }
    String[][] results = new String[section.size()][2];
    int i = 0;
    Enumeration f = section.keys(); for (Enumeration g = section.elements(); f
      .hasMoreElements(); 
      i++) {
      results[i][0] = ((String)f.nextElement());
      results[i][1] = ((String)g.nextElement());
    }
    return results;
  }

  public void load(String filename)
    throws FileNotFoundException
  {
    load(new FileInputStream(filename));
  }

  public void save(String filename)
    throws IOException
  {
    save(new FileOutputStream(filename));
  }

  public void load(InputStream in)
  {
    try
    {
      BufferedReader input = new BufferedReader(new InputStreamReader(in));

      Hashtable section = null;
      String read;
      while ((read = input.readLine()) != null)
      {
        if ((!read.startsWith(";")) && (!read.startsWith("#")))
        {
          if (read.startsWith("["))
          {
            String section_name = read.substring(1, read.indexOf("]"))
              .toLowerCase();
            section = this.sections.get(section_name);
            if (section == null) {
              section = new Hashtable();
              this.sections.put(section_name, section);
            }
          } else if ((read.indexOf("=") != -1) && (section != null))
          {
            String key = read.substring(0, read.indexOf("=")).trim()
              .toLowerCase();
            String value = read.substring(read.indexOf("=") + 1).trim();
            section.put(key, value);
          }
        }
      }
    } catch (Exception e) { e.printStackTrace(); }

  }

  public void save(OutputStream out)
  {
    try
    {
      PrintWriter output = new PrintWriter(out);
      Enumeration f;
      for (Enumeration e = this.sections.keys(); e.hasMoreElements(); 
        f.hasMoreElements())
      {
        String section = (String)e.nextElement();
        output.println("[" + section + "]");
        f = getSection(section).keys();
        Enumeration g = getSection(
          section).elements(); continue;
      }

      output.flush();
      output.close();
      out.flush();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void addSection(String section)
  {
    this.sections.put(section.toLowerCase(), new Hashtable());
  }

  public void removeSection(String section)
  {
  }

  public static void main(String[] args)
    throws Exception
  {
    IniFile ini = new IniFile();
    ini.load(new FileInputStream("c:\\VCCBBANK.ini"));
    System.out.println(ini.getKeyValue("TRADEID", "counter"));
  }
}
