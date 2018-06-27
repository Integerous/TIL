public class Xbonacci {
      
  public double[] tribonacci(double[] s, int n) {
      // hackonacci me
      

      
      double[] x = new double[n];
      
      if(n>2){
      
        x[0] = s[0];
        x[1] = s[1];
        x[2] = s[2];
        
        for(int i=3; i<n; i++)
          x[i] = x[i-3] + x[i-2] + x[i-1]; 
      }else if(n==2){
        x[0] = s[0];
        x[1] = s[1];
      }else if(n==1)
        x[0] = s[0];
      
      
      return x;
  }
}
