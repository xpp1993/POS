package com.lxkj.administrator.pos.utils;

/**
 * ����ѡ����
 * @author levy
 *
 */
public class CodeUtils {
	public static final String[] sexCode={"δ֪","��","Ů"};
	public static final String[] nationCode={
		"δ֪",//0
		"����",//1
		"�ɹ���",//2
		"����",//3
		"����",
		"ά�����",
		"����",
		"����",
		"׳��",
		"������",
		"������",
		"����",
		"����",
		"����",
		"����",
		"������",  
        "������",
        "��������",
        "����",
        "����",
        "������",
        "����",
        "���",
        "��ɽ��",
        "������",
        "ˮ��",
        "������",
        "������",
        "������",
        "�¶�������",  
        "����",
        "���Ӷ���",
        "������",
        "Ǽ��",
        "������",
        "������",
        "ë����",
        "������",
        "������",
        "������",
        "������",
        "��������",
        "ŭ��", 
        "���α����",  
       "����˹��",
       "���¿���",
       "�°���",
       "������",
       "ԣ����",
       "����",
       "��������",
       "������",
       "���״���",
       "������",
       "�Ű���",
       "�����",
       "��ŵ��"};

	/**
	 * ���ݱ����ȡ��Ӧ����
	 * @param nation
	 * @return ��Ӧ����
	 */
	public static String getNationNameById(String nation) {
		String str="��";
		int code=Integer.parseInt(nation);
		return str=nationCode[code];		
		
	}
	/**
	 *  �����Ա������Ա�
	 * @param gender
	 * @return�Ա�'δ֪'or'��'or'Ů'
	 */
	public static String getGender(String gender) {
		String str="δ֪";
		int code=Integer.parseInt(gender);
		return str=sexCode[code];

	}
	

}
