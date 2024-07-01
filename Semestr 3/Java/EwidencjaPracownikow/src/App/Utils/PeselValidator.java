package App.Utils;

import java.util.Set;

public class PeselValidator {
    private final Set<String> pesels;
    public PeselValidator(Set<String> pesels) {this.pesels = pesels;}
    public boolean validatePesel(String pesel){
        if(pesel.length()!=11){
            return false;
        }
        if(pesels.contains(pesel)){
            return false;
        }
        int[] weights = {1,3,7,9,1,3,7,9,1,3};
        int sum=0;
        for(int i=0;i<10;i++){
            sum+=weights[i]*Integer.parseInt(pesel.substring(i,i+1));
        }
        sum%=10;
        sum=10-sum;
        sum%=10;
        return sum==Integer.parseInt(pesel.substring(10));
    }
}
