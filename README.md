# OpenFinDesk: Algorithmic Trading based on Technical Analysis
## An Opensource framework to trade Forex and CFD using technical analysis in Java
This is an opensource Java framework to implement any technical trading strategy in less than a minute
by using openFinDesk annotations.
OpenFinDesk has many modules. Current module which is based on technical analysis is opensource.

#pros
* loose coupling and Java SOLID principles are considered as much as possible.
* can be scaled horizontally and vertically
* using influxdb,csv or any other store you like.
* complete backtesting before running in live mode
* a module to use Hidden Markov Model
* customized log levels
* testing java classes
* separate risk management 
* separate money management 
* separate position management
* using any approach such as Fibonacci, indicators or a combination of many strategies
#cons
* It is only technical analysis.
* It is not based on a reactive architecture like RxJava.
* refactoring is not perfect.
### Example1
```
@OpenFinDeskChanceNode(containerId=5,nodeId=0
        ,openfindeskStrategies={CamarillaStrategy.class, ADXBasedTrendDetection.class}
        ,timeFrames={"PERIOD_D1","PERIOD_H1"})
public class CheckIfCamarillaAndADX extends ChanceNode {
    final static Logger logger= Logger.getLogger(CheckIfCamarillaAndADX.class);

    @Override
    public void executeCurrentChanceNode() {
        this.openFinDeskOrder.setSymbol(featureVector.get(0).getSymbol());
        this.openFinDeskOrder.setPosition("*");
        this.openFinDeskOrder.setOrderType("noOrder");
        this.openFinDeskOrder.setStrategyName("no signal from container 5");
        this.openFinDeskOrder.setAction("doNothingAndWait");
        this.openFinDeskOrder.setVolume("0.01");
        logger.info("featureVector.get(0).getPosition()="+featureVector.get(0).getPosition());
        logger.info("featureVector.get(1).getPosition()="+featureVector.get(1).getPosition());

        if ((featureVector.get(0).getPosition().equals("buy")) && (featureVector.get(1).getPosition().equals("buy"))) {
            this.openFinDeskOrder.setOrderType("openBuy");
            this.openFinDeskOrder.setAction("buyUsingFirstQuantizedLevel");
            this.openFinDeskOrder.setStrategyName("Container5");
        }else if ((featureVector.get(0).getPosition().equals("sell")) && (featureVector.get(1).getPosition().equals("sell"))) {
            this.openFinDeskOrder.setOrderType("openSell");
            this.openFinDeskOrder.setAction("sellUsingFirstQuantizedLevel");
            this.openFinDeskOrder.setStrategyName("Container5");
        }else {
            this.openFinDeskOrder.setOrderType("noOrder");
            this.openFinDeskOrder.setAction("doNothingAndWait");
        }
        decisionIsMade=true;

    }
}
This informs the software to use CamarillaStrategy in daily time frame and ADX in H1 timeframe
```
### Example2
```
@OpenFinDeskChanceNode(containerId=1,nodeId=0
,openfindeskStrategies={PinBarStrategy.class,TrendStrengthStrategyBasedOnInternalRetracementSequences.class}
,timeFrames={"PERIOD_H1","PERIOD_D1"},disabled=true)
public class CheckIfWeHavePinBarCandlestickChanceNode extends ChanceNode {
    final static Logger logger= Logger.getLogger(CheckIfWeHavePinBarCandlestickChanceNode.class);

    @Override
    public void executeCurrentChanceNode() {
        this.openFinDeskOrder.setSymbol(featureVector.get(0).getSymbol());
        this.openFinDeskOrder.setPosition("*");
        this.openFinDeskOrder.setOrderType("noOrder");
        this.openFinDeskOrder.setStrategyName("no signal from container 1");
        this.openFinDeskOrder.setAction("doNothingAndWait");
        this.openFinDeskOrder.setVolume("0.01");
        logger.info("featureVector.get(0).getPosition()="+featureVector.get(0).getPosition());
        logger.info("featureVector.get(1).getPosition()="+featureVector.get(1).getPosition());
        if ((featureVector.get(0).getPosition().equals("bearishPinBar"))&&(featureVector.get(1).getPosition().equals("sell"))){
            this.openFinDeskOrder.setOrderType("openSell");
            this.openFinDeskOrder.setAction("sellUsingScalping");
            this.openFinDeskOrder.setStrategyName("container1");
        }else if((featureVector.get(0).getPosition().equals("bullishPinBar"))&&(featureVector.get(1).getPosition().equals("buy"))){
            this.openFinDeskOrder.setOrderType("openBuy");
            this.openFinDeskOrder.setAction("buyUsingScalping");
            this.openFinDeskOrder.setStrategyName("container1");
        }
        decisionIsMade=true;

    }
}
This informs the software to use PinBarStrategy in H1 time frame and TrendStrengthStrategyBasedOnInternalRetracementSequences in Daily timeframe
```
of course you can also use more complex strategies since the infrastructure is based on datastructure of decision tree
and you can create any decision tree.
# v8.dll
since i created a dll using visual C++, i used a release version since
if i put debug version,then i need a  Visual C++ Redistributable on each computer.
If you dont create a release version for your dll then MQL5 keeps saying: can not open dll even
if you have allowed MQL5 to do so.

#mql5
This java infrastructure connects to mql5 via sockets. Mql5 uses your dll file. I created v8.dll based on my C++ codes.
# order
## separate by comma:
7 parameters: 
order,symbol,orderType,executionType,tp,sl,volume

* order : "order"
* symbol: "EURUSD"
* orderType:"ORDER_TYPE_BUY"
* executionType:"instant" or "pending"
* tp:"1.11034"
* sl:"1.10226"
* volume:"0.01" 
