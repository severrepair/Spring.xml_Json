package com.bitc.xmltest.controller;

import com.bitc.xmltest.dto.DailyBoxOffice;
import com.bitc.xmltest.dto.PharmacyFullDateItemDto;
import com.bitc.xmltest.service.BoxOfficeService;
import com.bitc.xmltest.service.PharmacyFullDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

//  jaxb : 자바에서 xml 데이터를 파싱을 도와주는 라이브러리
//  Marshal(마샬) : 자바 클래스를 XML 데이터로 변환
//  UNMarshal(언마샬) : XML 데이터를 자바 클래스 타입의 객체로 변환(PharmacyFullDataServiceImpl확인해볼것)

//  @XmlRootElement : xml 데이터에서 부모가 되는 태그를 뜻함
//  @XmlElement : xml 데이터에서 실제 데이터가 들어있는 태그를 뜻함
//  @XmlAttribute : xml 데이터에서 지정한 태그의 속성을 뜻함

@Controller
public class PharmacyController {
    @Autowired
    private BoxOfficeService boxOfficeService;

    @Autowired
    private PharmacyFullDataService pharmacyFullDataService;

    @Value("${openApi.endPoint}")
    private String endPointUrl;

    @Value("${openApi.serviceKey}")
    private String serviceKey;

    @RequestMapping("/")
    public String index() throws Exception {
        return "index";
    }

    @RequestMapping(value = "/pharmacy/fullDataFile", method = RequestMethod.GET)
    public ModelAndView getFullData() throws Exception {
        ModelAndView mv = new ModelAndView("pharmacy/fullDataFile");

        List<PharmacyFullDateItemDto> itemList = pharmacyFullDataService.getItemList();
        mv.addObject("pharmacyDatas", itemList);

        return mv;
    }

    @RequestMapping(value = "/pharmacy/fullDataUrl", method = RequestMethod.GET)
    public String viewFullData() throws Exception {
        return "pharmacy/fullDataUrl";
    }

    @ResponseBody
    @RequestMapping(value = "/pharmacy/fullDataUrl", method = RequestMethod.POST)
    public Object getFullDataAjax() throws Exception {
//      open api 서버로 요청하기 위한 url 생성

        String reqService = "/getParmacyFullDown?";
        String service = "?serviceKey=";
        String option1 = "&pageNo=";
        String option2 = "&numOfRows=";

//        문제 1) fullDataUrl.html 에 출력페이지 번호와, 한 페이지에 출력할 수를 입력하는 input 태그를 생헝하고 버튼 클릭 시 해당 페이지와 데이터 수를 가져오는 방식으로 변경하세요.
//        html 페이지에 input 태그 2개(혹은 input 1개, select1개) 생성
//        controller에서 매개변수를 받아주기 위한 부분 추가
//        open api url 생성부분에 매개변수로 받은 데이터 적용

        String url = endPointUrl + reqService + service + serviceKey + option1 + 1 + option2 + 10;

        List<PharmacyFullDateItemDto> pharmacyDatas = pharmacyFullDataService.getItemListUrl(url);

        return pharmacyDatas;
    }

    @RequestMapping(value = "/movie/dailyBoxOffice", method = RequestMethod.GET)
    public String dailyBoxOfficeView() {
        return "/movie/dailyBoxOffice";
    }

    @ResponseBody
    @RequestMapping(value = "/movie/dailyBoxOfficeJson", method = RequestMethod.POST)
    public Object getDailyBoxOfficeListJson() {
        String url = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20221122";

        List<DailyBoxOffice> dailyBoxOfficeList = boxOfficeService.getDailyBoxOfficeListJson(url);

        return dailyBoxOfficeList;
    }
}
