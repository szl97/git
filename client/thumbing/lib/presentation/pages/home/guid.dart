import 'package:flutter/material.dart';
import 'package:flutter_icons/flutter_icons.dart';
import 'package:thumbing/presentation/pages/personal/personal.dart';
import 'package:thumbing/presentation/pages/home/home.dart';
import 'package:thumbing/presentation/pages/message/message.dart';
import 'package:thumbing/presentation/pages/roast/harbor.dart';
import 'package:thumbing/presentation/util/screen_utils.dart';

class Guid extends StatefulWidget {
  final index;

  Guid({Key key, this.index = 0}) : super(key: key);

  @override
  _GuidState createState() => _GuidState(this.index);
}

class _GuidState extends State<Guid> {
  final PageController _pageController = PageController();
  int currentIndex;
  _GuidState(index) {
    this.currentIndex = index;
  }
  @override
  void dispose() {
    _pageController.dispose();
    super.dispose();
  }
  List<Widget> pageList = [
    Home(),
    Harbor(),
    Message(),
    Personal(name: "Stan Sai"),
  ];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: Container(
        height: ScreenUtils.getInstance().getHeight(55),
        width: ScreenUtils.getInstance().getWidth(55),
        padding: EdgeInsets.only(bottom: ScreenUtils.getInstance().getHeight(8), top: ScreenUtils.getInstance().getHeight(1), left: ScreenUtils.getInstance().getHeight(1), right: ScreenUtils.getInstance().getHeight(1)),
        margin: EdgeInsets.only(top: ScreenUtils.getInstance().getHeight(10)),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(40),
          color: Colors.blueAccent,
        ),
        child: FloatingActionButton(
          child: Icon(Icons.add),
          onPressed: () {
            Navigator.pushNamed(context, '/content/pushContent');
          },
          backgroundColor: Colors.blueAccent,
        ),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerDocked,
      body: PageView(
        physics: const NeverScrollableScrollPhysics(), // 禁止滑动
        controller: _pageController,
        onPageChanged: (int index) => setState(()=>this.currentIndex=index),
        children: this.pageList,
      ),
      bottomNavigationBar: BottomNavigationBar(
          currentIndex: this.currentIndex, //配置对应的索引值选中
          onTap: (int index) => _pageController.jumpToPage(index),
          iconSize: 20.0, //icon的大小
          backgroundColor: Colors.blueAccent,
          unselectedItemColor: Colors.white,
          fixedColor: Colors.yellow, //选中的颜色
          type: BottomNavigationBarType.fixed, //配置底部tabs可以有多个按钮
          items: [
            BottomNavigationBarItem(icon: Icon(Icons.home), title: Text("首页")),
            BottomNavigationBarItem(
                icon: Icon(MaterialCommunityIcons.sailing), title: Text("港湾")),
            BottomNavigationBarItem(
                icon: Icon(Icons.message), title: Text("消息")),
            BottomNavigationBarItem(
                icon: Icon(Icons.person), title: Text("我的")),
          ]),
    );
  }
}
