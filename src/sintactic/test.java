public void report_error(String message, Object info) {
    StringBuilder errorMessage = new StringBuilder("Error ");

    if (info instanceof java_cup.runtime.Symbol) 
      errorMessage.append( "(" + info.toString()+")" );
     
    errorMessage.append(" : " + message);
   
    System.out.println(errorMessage);
  }
   
  public void report_fatal_error(String message, Object info) {
    report_error(message, info);
    throw new RuntimeException("Fatal Syntax Error");
  }