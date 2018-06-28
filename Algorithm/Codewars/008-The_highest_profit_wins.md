class MinMax {
    public static int[] minMax(int[] arr) {
        
        int min = arr[0];
        int max = arr[0];
        
        for(int i=1; i<arr.length; i++) {
          if( arr[i] > max)
            max = arr[i];
          if( arr[i] < min)
            min = arr[i];
        }
            
        int[] result = new int[2];
            result[0] = min;
            result[1] = max;
            
        return result;
    }
}
