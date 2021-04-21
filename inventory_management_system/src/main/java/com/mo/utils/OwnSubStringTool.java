package com.mo.utils;

import com.mo.pojo.MInOutRepository;
import com.mo.pojo.PInOutRepository;

import java.util.Iterator;
import java.util.List;

/**
 * 用于 3,4,1 此类字符串删除某个元素的处理
 */
public class OwnSubStringTool {
    public static String listToString(List<String> ls) {
        String str = "";
        for (String s : ls) {
            str = str + s + ",";
        }
        return str.substring(0, str.length() - 1);
    }

    /**
     * 在一个字符串中剔除符合条件的字符，返回不包含符合条件的字符串
     *
     * @param inRepository
     * @param character    需要排除的字符串
     * @return
     */
    public static MInOutRepository eliminateString(MInOutRepository inRepository, String character) {
        String str = inRepository.getMaterial_id();
        //把要修改的字符串提取出来转为list
        List<String> material_id = MySubString.subString(str, ",");
        List<String> unit_price = MySubString.subString(inRepository.getUnit_price(), ",");
        List<String> quantity = MySubString.subString(inRepository.getQuantity(), ",");
        List<String> supplier_id = MySubString.subString(inRepository.getSupplier_id(), ",");
        int index = 0;
        //字符串长度为 1 直接返回null
        if (str.length() != 1) {
            //使用迭代器，找出相同的字符并删除掉
            Iterator<String> is = material_id.iterator();
            while (is.hasNext()) {
                String s = is.next();

                if (s.equals(character)) {
                    //删除要删除的相应数据
                    is.remove();
                    unit_price.remove(index);
                    quantity.remove(index);
                    supplier_id.remove(index);
                    break;
                }
                ++index;
            }
        } else
            return null;
        //把list转为字符串
        inRepository.setMaterial_id(listToString(material_id));
        inRepository.setUnit_price(listToString(unit_price));
        inRepository.setQuantity(listToString(quantity));
        inRepository.setSupplier_id(listToString(supplier_id));

        return inRepository;
    }

    /**
     * 在一个字符串中剔除符合条件的字符，返回不包含符合条件的字符串
     *
     * @param pInOutRepository
     * @param character        需要排除的字符串
     * @return
     */
    public static PInOutRepository eliminateStringP(PInOutRepository pInOutRepository, String character) {
        String str = pInOutRepository.getProduct_id();
        //把要修改的字符串提取出来转为list
        List<String> product_id = MySubString.subString(str, ",");
        List<String> unit_price = MySubString.subString(pInOutRepository.getUnit_price(), ",");
        List<String> quantity = MySubString.subString(pInOutRepository.getQuantity(), ",");
        int index = 0;
        //字符串长度为 1 直接返回null
        if (str.length() != 1) {
            //使用迭代器，找出相同的字符并删除掉
            Iterator<String> is = product_id.iterator();
            while (is.hasNext()) {
                String s = is.next();
                if (s.equals(character)) {
                    //删除要删除的相应数据
                    is.remove();
                    unit_price.remove(index);
                    quantity.remove(index);
                    break;
                }
                ++index;
            }
        } else
            return null;
        //把list转为字符串
        pInOutRepository.setProduct_id(listToString(product_id));
        pInOutRepository.setUnit_price(listToString(unit_price));
        pInOutRepository.setQuantity(listToString(quantity));

        return pInOutRepository;
    }
}
