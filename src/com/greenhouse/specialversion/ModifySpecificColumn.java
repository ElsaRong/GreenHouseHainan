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
	

//		public static void modifyControllerJackNameTest1(String mac) {
//			JackService jackService = new JackService(GreenHouseApplication.getContext());
//			jackService.modifyControllerJackNameTest("������\n�̿�", 1, mac);
//			jackService.modifyControllerJackNameTest("������\n����", 2, mac);
//			jackService.modifyControllerJackNameTest("������\n�̿�", 3, mac);
//			jackService.modifyControllerJackNameTest("������\n����", 4, mac);
//			jackService.modifyControllerJackNameTest("������\n�̿�", 5, mac);
//			jackService.modifyControllerJackNameTest("������\n����", 6, mac);
//			jackService.modifyControllerJackNameTest("ˮ���ھ�\n�̿�", 7, mac);
//			jackService.modifyControllerJackNameTest("ˮ���ھ�\n����", 8, mac);
//			jackService.modifyControllerJackNameTest("ˮ�����\n�̿�", 9, mac);
//			jackService.modifyControllerJackNameTest("ˮ�����\n����", 10, mac);
//			jackService.modifyControllerJackNameTest("�ڱ���\n�̿�", 11, mac);
//			jackService.modifyControllerJackNameTest("�ڱ���\n����", 12, mac);
//			jackService.modifyControllerJackNameTest("1�ŷ��", 13, mac);
//			jackService.modifyControllerJackNameTest("2�ŷ��", 14, mac);
//			jackService.modifyControllerJackNameTest("3�ŷ��", 15, mac);
//			jackService.modifyControllerJackNameTest("ˮ��ˮ��", 16, mac);
//			jackService.modifyControllerJackNameTest("����ˮ��", 17, mac);
//			jackService.modifyControllerJackNameTest("��ѭ��\n���", 18, mac);
//			jackService.modifyControllerJackNameTest("��ѭ��\n���", 19, mac);
//			jackService.modifyControllerJackNameTest("1�ŷ�", 20, mac);
//			jackService.modifyControllerJackNameTest("2�ŷ�", 21, mac);
//			jackService.modifyControllerJackNameTest("3�ŷ�", 22, mac);
//			jackService.modifyControllerJackNameTest("4�ŷ�", 23, mac);
//			jackService.modifyControllerJackNameTest("5�ŷ�", 24, mac);
//			jackService.modifyControllerJackNameTest("6�ŷ�", 25, mac);
//			jackService.modifyControllerJackNameTest("7�ŷ�", 26, mac);
//			jackService.modifyControllerJackNameTest("8�ŷ�", 27, mac);
//			jackService.modifyControllerJackNameTest("9�ŷ�", 28, mac);
//			jackService.modifyControllerJackNameTest("10�ŷ�", 29, mac);
//			jackService.modifyControllerJackNameTest("11�ŷ�", 30, mac);
//			jackService.modifyControllerJackNameTest("12�ŷ�", 31, mac);
//			jackService.modifyControllerJackNameTest("13�ŷ�", 32, mac);
//			jackService.modifyControllerJackNameTest("14�ŷ�", 33, mac);
//			jackService.modifyControllerJackNameTest("15�ŷ�", 34, mac);
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
