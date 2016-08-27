package com.greenhouse.specialversion;

import com.greenhouse.database.JackService;
import com.greenhouse.util.GreenHouseApplication;

/** 
* @author       Elsa 
* @Email		elsarong715@gmail.com
* @date			2016��4��15������10:17:03 
* @version		1.0  
* @description			 
*/
public class ModifySpecificColumn {

//	public static void modifyJackBund(String mac) {
//		JackService jackService = new JackService(GreenHouseApplication.getContext());
//		jackService.modifyJackBund(1, 5, mac);
//		jackService.modifyJackBund(1, 6, mac);
//		jackService.modifyJackBund(1, 7, mac);
//		jackService.modifyJackBund(1, 8, mac);
//	}
	
	/**
	 * @Title:       modifyControllerJackNameTest1
	 * @description: TODO 一号柜命名
	 * @param        @param mac
	 * @return       void
	 * @throws
	 * @author       Elsa elsarong715@gmail.com
	 * @data         Aug 27, 2016, 6:36:54 PM
	 */
	public static void modifyControllerJackNameTest1(String mac) {
		JackService jackService = new JackService(GreenHouseApplication.getContext());
		jackService.modifyControllerJackNameTest("内遮阳\n铺开", 1, mac);
		jackService.modifyControllerJackNameTest("内遮阳\n收起", 2, mac);
		jackService.modifyControllerJackNameTest("中遮阳\n铺开", 3, mac);
		jackService.modifyControllerJackNameTest("中遮阳\n收起", 4, mac);
		jackService.modifyControllerJackNameTest("外遮阳\n铺开", 5, mac);
		jackService.modifyControllerJackNameTest("外遮阳\n收起", 6, mac);
		jackService.modifyControllerJackNameTest("水帘内卷\n铺开", 7, mac);
		jackService.modifyControllerJackNameTest("水帘内卷\n收起", 8, mac);
		jackService.modifyControllerJackNameTest("水帘外卷\n铺开", 9, mac);
		jackService.modifyControllerJackNameTest("水帘外卷\n收起", 10, mac);
		jackService.modifyControllerJackNameTest("内保温\n铺开", 11, mac);
		jackService.modifyControllerJackNameTest("内保温\n收起", 12, mac);
		jackService.modifyControllerJackNameTest("1号风机\n运行", 13, mac);
		jackService.modifyControllerJackNameTest("2号风机\n运行", 14, mac);
		jackService.modifyControllerJackNameTest("3号风机\n运行", 15, mac);
		jackService.modifyControllerJackNameTest("内循环风机\n运行", 16, mac);
		jackService.modifyControllerJackNameTest("外循环风机\n运行", 17, mac);
		jackService.modifyControllerJackNameTest("水帘水泵\n运行", 18, mac);
		jackService.modifyControllerJackNameTest("水帘水泵\n运行", 19, mac);
		jackService.modifyControllerJackNameTest("1号阀", 20, mac);
		jackService.modifyControllerJackNameTest("2号阀", 21, mac);
		jackService.modifyControllerJackNameTest("3号阀", 22, mac);
		jackService.modifyControllerJackNameTest("4号阀", 23, mac);
		jackService.modifyControllerJackNameTest("5号阀", 24, mac);
		jackService.modifyControllerJackNameTest("6号阀", 25, mac);
		jackService.modifyControllerJackNameTest("7号阀", 26, mac);
		jackService.modifyControllerJackNameTest("8号阀", 27, mac);
		jackService.modifyControllerJackNameTest("9号阀", 28, mac);
		jackService.modifyControllerJackNameTest("10号阀", 29, mac);
		jackService.modifyControllerJackNameTest("11号阀", 30, mac);
		jackService.modifyControllerJackNameTest("12号阀", 31, mac);
		jackService.modifyControllerJackNameTest("13号阀", 32, mac);
		jackService.modifyControllerJackNameTest("14号阀", 33, mac);
		jackService.modifyControllerJackNameTest("15号阀", 34, mac);
//			jackService.modifyControllerJackNameTest("35", 35, mac);
//			jackService.modifyControllerJackNameTest("36", 36, mac);
//			jackService.modifyControllerJackNameTest("37", 37, mac);
//			jackService.modifyControllerJackNameTest("38", 38, mac);
//			jackService.modifyControllerJackNameTest("39", 39, mac);
//			jackService.modifyControllerJackNameTest("40", 40, mac);
//			jackService.modifyControllerJackNameTest("41", 41, mac);
//			jackService.modifyControllerJackNameTest("42", 42, mac);
//			jackService.modifyControllerJackNameTest("43", 43, mac);
//			jackService.modifyControllerJackNameTest("44", 44, mac);
//			jackService.modifyControllerJackNameTest("45", 45, mac);
//			jackService.modifyControllerJackNameTest("46", 46, mac);
//			jackService.modifyControllerJackNameTest("47", 47, mac);
//			jackService.modifyControllerJackNameTest("48", 48, mac);
	}

		
//		public static void modifyControllerJackDrawableTest1(String mac) {
//			JackService jackService = new JackService(GreenHouseApplication.getContext());
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_hps", 1, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_hps", 2, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_hps", 3, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_hps", 4, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_hps", 5, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_hps", 6, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_water_pump", 7, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_water_pump", 8, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_water_pump", 9, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_water_pump", 10, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_air_conditioning", 11, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_air_conditioning", 12, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_fanner", 13, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_fanner", 14, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_fanner", 15, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_water_pump", 16, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_water_pump", 17, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_fan", 18, mac);
//			jackService.modifyControllerJackDrawableTest("R.drawable.gray_fan", 19, mac);
////			jackService.modifyControllerJackDrawableTest(, 20, mac);
////			jackService.modifyControllerJackDrawableTest("2�ŷ�", 21, mac);
////			jackService.modifyControllerJackDrawableTest("3�ŷ�", 22, mac);
////			jackService.modifyControllerJackDrawableTest("4�ŷ�", 23, mac);
////			jackService.modifyControllerJackDrawableTest("5�ŷ�", 24, mac);
////			jackService.modifyControllerJackDrawableTest("6�ŷ�", 25, mac);
////			jackService.modifyControllerJackDrawableTest("7�ŷ�", 26, mac);
////			jackService.modifyControllerJackDrawableTest("8�ŷ�", 27, mac);
////			jackService.modifyControllerJackDrawableTest("9�ŷ�", 28, mac);
////			jackService.modifyControllerJackDrawableTest("10�ŷ�", 29, mac);
////			jackService.modifyControllerJackDrawableTest("11�ŷ�", 30, mac);
////			jackService.modifyControllerJackDrawableTest("12�ŷ�", 31, mac);
////			jackService.modifyControllerJackDrawableTest("13�ŷ�", 32, mac);
////			jackService.modifyControllerJackDrawableTest("14�ŷ�", 33, mac);
////			jackService.modifyControllerJackDrawableTest("15�ŷ�", 34, mac);
//		}
		
		
		// ʵ���ϵ�2����
//		public static void modifyControllerJackNameTest2(String mac) {
//			JackService jackService = new JackService(GreenHouseApplication.getContext());
//			jackService.modifyControllerJackNameTest("ˮ����Ĥ�̿�����", 1, mac);
//			jackService.modifyControllerJackNameTest("ˮ����Ĥ��������", 2, mac);
//			jackService.modifyControllerJackNameTest("������Ĥ�̿�����", 3, mac);
//			jackService.modifyControllerJackNameTest("������Ĥ��������", 4, mac);
//			jackService.modifyControllerJackNameTest("�������̿�����", 5, mac);
//			jackService.modifyControllerJackNameTest("��������������", 6, mac);
//			jackService.modifyControllerJackNameTest("�������̿�����", 7, mac);
//			jackService.modifyControllerJackNameTest("��������������", 8, mac);
//			jackService.modifyControllerJackNameTest("�������̿�����", 9, mac);
//			jackService.modifyControllerJackNameTest("��������������", 10, mac);
//			jackService.modifyControllerJackNameTest("ˮ����Ĥ�̿�����", 11, mac);
//			jackService.modifyControllerJackNameTest("ˮ����Ĥ��������", 12, mac);
//			jackService.modifyControllerJackNameTest("�������", 13, mac);
//			jackService.modifyControllerJackNameTest("ˮ��ˮ������", 14, mac);
//			jackService.modifyControllerJackNameTest("����ˮ������", 15, mac);
//			jackService.modifyControllerJackNameTest("��ѭ���������", 16, mac);
//			jackService.modifyControllerJackNameTest("17", 17, mac);
//			jackService.modifyControllerJackNameTest("18", 18, mac);
//			jackService.modifyControllerJackNameTest("1�ŷ�����", 19, mac);
//			jackService.modifyControllerJackNameTest("2�ŷ�����", 20, mac);
//			jackService.modifyControllerJackNameTest("3�ŷ�����", 21, mac);
//			jackService.modifyControllerJackNameTest("4�ŷ�����", 22, mac);
//			jackService.modifyControllerJackNameTest("5�ŷ�����", 23, mac);
//			jackService.modifyControllerJackNameTest("6�ŷ�����", 24, mac);
//			jackService.modifyControllerJackNameTest("7�ŷ�����", 25, mac);
//			jackService.modifyControllerJackNameTest("8�ŷ�����", 26, mac);
//			jackService.modifyControllerJackNameTest("9�ŷ�����", 27, mac);
//			jackService.modifyControllerJackNameTest("10�ŷ�����", 28, mac);
//			jackService.modifyControllerJackNameTest("��ǽ��Ĥ�̿�����", 29, mac);
//			jackService.modifyControllerJackNameTest("��ǽ��Ĥ��������", 30, mac);
//			jackService.modifyControllerJackNameTest("31", 31, mac);
//			jackService.modifyControllerJackNameTest("32", 32, mac);
//			jackService.modifyControllerJackNameTest("33", 33, mac);
//			jackService.modifyControllerJackNameTest("34", 34, mac);
//			jackService.modifyControllerJackNameTest("35", 35, mac);
//			jackService.modifyControllerJackNameTest("36", 36, mac);
//			jackService.modifyControllerJackNameTest("37", 37, mac);
//			jackService.modifyControllerJackNameTest("38", 38, mac);
//			jackService.modifyControllerJackNameTest("39", 39, mac);
//			jackService.modifyControllerJackNameTest("40", 40, mac);
//			jackService.modifyControllerJackNameTest("41", 41, mac);
//			jackService.modifyControllerJackNameTest("42", 42, mac);
//			jackService.modifyControllerJackNameTest("43", 43, mac);
//			jackService.modifyControllerJackNameTest("44", 44, mac);
//			jackService.modifyControllerJackNameTest("45", 45, mac);
//			jackService.modifyControllerJackNameTest("46", 46, mac);
//			jackService.modifyControllerJackNameTest("47", 47, mac);
//			jackService.modifyControllerJackNameTest("48", 48, mac);
//		}
	
}
