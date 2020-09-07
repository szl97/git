import 'package:thumbing/generated/json/base/json_convert_content.dart';

class PageRequestEntity with JsonConvert<PageRequestEntity> {
	PageRequestEntity({this.position,this.pageNumber, this.pageSize});
	int pageNumber;
	int pageSize;
	int position;
}
