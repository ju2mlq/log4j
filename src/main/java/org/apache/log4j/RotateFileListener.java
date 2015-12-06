package org.apache.log4j;

import java.io.File;

public interface RotateFileListener {
  public void postFile(File f);
}
