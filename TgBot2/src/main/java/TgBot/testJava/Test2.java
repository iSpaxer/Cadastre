package TgBot.testJava;

import TgBot.service.impl.DistributionServiceImpl;

class test2_1 {
    private DistributionServiceImpl distributionService;

    public DistributionServiceImpl returnDist() {
        return distributionService;
    }
}

public class Test2 extends test2_1{

    Test2(DistributionServiceImpl distributionService) {
//        super(distributionService);
    }
}
