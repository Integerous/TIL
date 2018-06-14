> 입출력을 대충 공부하고 복붙으로만 쓰다가 제대로 실습!

# 일반적인 I/O
~~~java
InputStream is = null;
OutputStream os = null;

try {
  is = new FileInputStream("/Users/ryanhan/test.txt");
  os = new FileOutputStream("/Users/ryanhan/test2.txt");

  while(true) {
    int i = is.read();
    if(i==-1)
      break;
    os.write(i);
  }
} catch (Exception e) {
  e.printStackTrace();
} finally {
  if(is != null) {
  try {
      is.close();
    } catch (Exception e2) {
      e2.printStackTrace();
    }
  } 
  if(os != null) {
    try {
      os.close();
    } catch (Exception e2) {
      e2.printStackTrace();
    }
  }
~~~

# byte[]를 활용하여 더 빠른 I/O
~~~java
InputStream is = null;
OutputStream os = null;

try {
  is = new FileInputStream("/Users/ryanhan/test.txt");
  os = new FileOutputStream("/Users/ryanhan/test2.txt");

  byte[] bs = new byte[5];

  while(true) {
    int count = is.read(bs);
    if(count == -1) {
      break;
    }
    os.write(bs, 0, count);
  }

} catch (Exception e) {
  e.printStackTrace();
} finally {
  if(is != null) {
    try {
      is.close();
    } catch (Exception e2) {
      e2.printStackTrace();
    }
  }
  if(os != null) {
    try {
      os.close();
    } catch (Exception e2) {
      e2.printStackTrace();
    }
  }
}
~~~

# Data I/O Stream을 이용한 문자열 읽고 쓰기
~~~java
InputStream is = null;
DataInputStream dis = null;

OutputStream os = null;
DataOutputStream dos = null;

try {
  is = new FileInputStream("Users/ryanhan/test.txt");
  dis = new DataInputStream(is);
  String str = dis.readUTF();

  os = new FileOutputStream("Users/ryanhan/test2.txt");
  dos = new DataOutputStream(os);
  dos.writeUTF(str);	

} catch (Exception e) {

} finally {
  if( dos != null) {
    try {
      dos.close();
    } catch (Exception e2) {
      e2.printStackTrace();
    }
  }
  if(os != null) {
    try {
      os.close();
    } catch (Exception e2) {
      e2.printStackTrace();
    }
  }

}
~~~
