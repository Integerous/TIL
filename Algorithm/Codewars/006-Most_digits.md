public class MostDigits {
  public static int findLongest(int[] numbers) {
    
    int max = numbers[0];
    
    for(int i=0; i<numbers.length; i++){
      if(Math.log10(numbers[i])+1 > Math.log10(max)+1)
        max = numbers[i];
        
      else if(Math.log10(numbers[i])+1 == Math.log10(max)+1)
        max = max;
    }
    
    return max;
  }
}
