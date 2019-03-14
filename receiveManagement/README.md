##看板管理系统
	**通过sqlserver使用sql语句获取字段信息,然后进行处理字段中的时间段
	  属于哪个区间和周几并且把同一时间段的数据进行去重进行保存成json
		
	**通过poi读取指定位置的excel表,从指定第6行第三列开始读取
	  判断属于哪个区间和周几保存成json数据,再进行sql语句把编码翻译成中文
	  供应商
	  
	**把每个excel和每个sql查询出来的字段进行互相比较,符合改变状态
	  excel存在而语句查询不存在也保存在json中


1.src/main/resources
	改变sqlserver地址
2.ExcelController类
	excelPath    改变读取地址
	path2		 改变存储位置
3.ReadExcel类
	excelPath	改变读取地址
	ROWNUM		初始化读取第几行开始  0开始
	CELLNUM     初始化列
	BERTHNUM	泊位总共多少时间段 0开始
	INTERVAL	间隔多少   如 供应商1  数量2 状态3   1开始
4.WriteExcel类
	ROWNUM		初始化读取第几行开始  0开始



5.日期都是以星期天开始到星期六为一周