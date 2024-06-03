package cy.olesiabokk.tradeisboomingapp.util;

import cy.olesiabokk.tradeisboomingapp.entity.JobType;

import java.util.Random;

public class Generator {
    private final Random random = new Random();

    public int getRandomNum(){
        return random.nextInt();
    }

    public int getRandomNum(int from, int to){
        int randomNum = getRandomNum();
        if(randomNum < from || randomNum > to){
            randomNum = (from + random.nextInt(to - from));
        }
        return randomNum;
    }

    public int addMaxCapacity(int from, int to){
        return getRandomNum(from, to);
    }

    public JobType getRandomJob(){
        JobType[] jobTypes = JobType.values();
        return  jobTypes[random.nextInt(jobTypes.length)];
    }
}
