import 'package:flutter/material.dart';

///文本搜索框
class SearchTextFieldWidget extends StatelessWidget {
  final ValueChanged<String> onSubmitted;
  final VoidCallback onTab;
  final String hintText;
  final EdgeInsetsGeometry margin;

  SearchTextFieldWidget(
      {Key key, this.hintText, this.onSubmitted, this.onTab, this.margin})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: margin == null ? EdgeInsets.all(0.0) : margin,
      width: MediaQuery.of(context).size.width,
      alignment: AlignmentDirectional.center,
      height: 37.0,
      decoration: BoxDecoration(
          color: Color.fromARGB(255, 237, 236, 237),
          borderRadius: BorderRadius.circular(24.0)),
      child: TextField(
        onSubmitted: onSubmitted,
        onTap: onTab,
        cursorColor: Colors.black,
        decoration: InputDecoration(
            contentPadding:
                EdgeInsets.only(left: 10, top: 5, bottom: 5, right: 10),
            border: InputBorder.none,
            hintText: hintText,
            hintStyle: TextStyle(
                fontSize: 17, color: Color.fromARGB(255, 192, 191, 191)),
            prefixIcon: Icon(
              Icons.search,
              size: 25,
              color: Color.fromARGB(255, 128, 128, 128),
            )),
        style: TextStyle(fontSize: 17),
        minLines: 1,
        maxLines: 5,
      ),
    );
  }

  getContainer(BuildContext context, ValueChanged<String> onSubmitted) {
    return Container(
      width: MediaQuery.of(context).size.width,
      alignment: AlignmentDirectional.center,
      height: 40.0,
      decoration: BoxDecoration(
          color: Color.fromARGB(255, 237, 236, 237),
          borderRadius: BorderRadius.circular(24.0)),
      child: TextField(
        onSubmitted: onSubmitted,
        cursorColor: Colors.black,
        decoration: InputDecoration(
            contentPadding:
                EdgeInsets.only(left: 10, top: 5, bottom: 5, right: 10),
            border: InputBorder.none,
            hintText: hintText,
            hintStyle: TextStyle(fontSize: 20),
            prefixIcon: Icon(
              Icons.search,
              size: 29,
              color: Color.fromARGB(255, 128, 128, 128),
            )),
        style: TextStyle(fontSize: 20),
        minLines: 1,
        maxLines: 5,
      ),
    );
  }
}
