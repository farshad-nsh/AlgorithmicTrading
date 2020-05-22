package com.farshad.algotrading.core.AlternatingTrendSmoothing;

 import com.farshad.algotrading.backtesting.measurements.timeSeriesMeasurements.PriceTime;
 import org.apache.log4j.Logger;

 import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ATS {
    final static Logger logger= Logger.getLogger(ATS.class);

    public List<PriceTime> priceChangeList;


    private int h;
    private List<PriceTime> priceTimeList;


    private double slope;

    public ATS(int h, List<PriceTime> priceTimeList) {
        this.h=h;
        this.priceTimeList=priceTimeList;
        this.priceChangeList=new ArrayList<>();

    }

    public void findPriceChangePoints() {
        Instant[] b=new Instant[700];

        double[] c=new double[b.length];
        float r=1;
         c[h]=priceTimeList.get(h).getPrice();
        slope = (c[h] - c[0]) ;

        r= (float) Math.signum(slope);

        int m=0;
        int d=0;
        while((m+h)<priceTimeList.size()) {
            b[m] =  priceTimeList.get(m).getTime();
            c[m] = priceTimeList.get(m).getPrice();
                    for (int i = m; i < m + h; i++) {
                        b[i + 1] = priceTimeList.get(i + 1).getTime();
                        c[i + 1] = priceTimeList.get(i + 1).getPrice();
                        slope = (c[i + 1] - c[m]);

                        if ((Math.signum(slope) == r)) {
                            logger.info("slope=" + slope);
                            d = i+1;
                        }else if(Math.signum(slope) != r){
                            d = i+1;
                            logger.info("break at d="+d);
                            break;
                        }
                    }
            if(d!=(m+h)) {
             //   if((d-m)>1){
                    priceChangeList.add(new PriceTime(c[m], b[m]));
                    r = r * (-1);
             //   }
            }

            m = d;
            logger.debug("m="+m+",r="+r);
        }
        //add last point to cover data
        priceChangeList.add(priceTimeList.get(priceTimeList.size()-1));
    }







    public List<PriceTime> getPriceChangeList() {
        logger.debug("priceChangeList.size()="+priceChangeList.size());
        logger.debug("priceTimeList.size()="+priceTimeList.size());
        priceTimeList.stream().forEach(priceTime -> {
            logger.debug("price="+priceTime.getPrice()+" ,time="+priceTime.getTime());
        });
        priceChangeList.stream().forEach(priceTime -> {
            logger.debug("price="+priceTime.getPrice()+" time:"+priceTime.getTime() );
        });
        return priceChangeList;
    }
}
