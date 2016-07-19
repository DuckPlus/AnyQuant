# AnyQuant 迭代三项目成果展示

---

## 项目介绍 ##
      AnyQuant是一款量化交易软件，向用户提供数据展示、个股分析，策略设计
    和回测，旨在为用户进行投资提供一定的帮助和支持。


----------


## 迭代三介绍 ##
      AnyQuant 迭代三是在迭代二的基础上开发的，我们重新开发了Web端，这也是
    我们小组第一次尝试开发Web端。
      迭代三是基于J2EE的Spring + SpringMVC + Hibernate 框架搭建的，使用
    MySQL作为数据库，感谢腾讯云提供的一元云服务器 ：）


----------
## 功能介绍 ##

 1. 查看市场行情
    提供A股市场两千多只股票历史数据的查看，包括开盘价、收盘价、成交量等日更数据。

    ![查看股票数据][1]

 2. 查看个股详细数据
    提供个股的个股基本信息（如上市日期、总股本、流通股本等等）、个股K线图、成交量图以及包括PE、PB在内的十余个因子的数据支持，并且支持以日期方式查询。

    ![个股详细界面][2]

 3. 个股综合分析和因子评价
      通过对股票近阶段因子的表现情况，从IC（信息系数）、IR（信息比率）等角度进
    行评判，通过雷达图的方式展示因子的有效性。<br/>
      综合分析则通过对因子的近期表现加权后得出一个总分。

      ![个股评分和因子图1][3]
      ![个股评分和因子图2][4]

 4. 行业分布及行业分析
    直观展示行业走势情况、行业所属股票的相关走势。
      ![行业分布图][5]
      ![行业分析图][6]
 5. 自选股
    提供自选股功能。用户可以注册登录后拥有自己的自选股数据，更及时的关注自己最关心的数据。   
    ![个股行业分布][7]
    ![个股地域分布][8]
 6. 策略与回测系统




策略与回测系统是本系统的核心，以为用户提供投资建议为目标，以A股两千多只个股的历
史数据作为测试对象，通过分析、回测得出策略的有效性，为用户的投资提供建议。

策略与回测系统主体分为两个部分，推荐策略和分析策略。
推荐策略提供一部分系统内建的策略，引导用户通过已有的、较为成熟的策略进行探索，从中获得建议。
分析策略基于个股的因子分析，从用户选择的股票池和时间段中通过系统的自动分析，得出股票池内的股票的因子在各个因子评价标准（如IC、IR、胜率）下的表现情况，通过图表的方式直观的向用户展示因子的有效性。
用户在获知因子的表现情况后调整因子的策略，根据因子的表现选择因子的权重值，使得股票的选择趋向于该因子。通过调整其他交易的参数，真实的模拟交易，通过历史交易数据对该策略进行回测，从而对策略进行评价。


----------
以下为效果图
![][9]
![][10]
![][11]
![][12]

------------
### 关于我们
![ICON][13]




  [1]: Documents/ScreenShots/StockList.png
  [2]: Documents/ScreenShots/StockDetail.png
  [3]: Documents/ScreenShots/FactorAnalysis.png
  [4]: Documents/ScreenShots/StockAnalysis.png
  [5]: Documents/ScreenShots/BoardDistribution.png
  [6]: Documents/ScreenShots/BoardDetail.png
  [7]: Documents/ScreenShots/OptionBoardDistribution.png
  [8]: Documents/ScreenShots/OptionRegionDistribution.png
  [9]: Documents/ScreenShots/Strategy.png
  [10]: Documents/ScreenShots/StrategyFactorAnalysis.png
  [11]: Documents/ScreenShots/StrategyResult1.png
  [12]: Documents/ScreenShots/StrategyResult2.png
  [13]: Documents/ScreenShots/duck_icon.png
