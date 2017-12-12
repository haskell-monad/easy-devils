package easy.devils.support.spring;


import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

/**
 * @author limengyu
 * @create 2017/11/23
 */
public class SpringUtils {

    /**
     * 获取beanId,如果id为空的话，则使用类名称(首字母小写)
     * @param element
     * @param clazz
     * @return
     */
    public static String getBeanId(Element element,Class<?> clazz){
        if(element == null){
            return StringUtils.uncapitalize(clazz.getSimpleName());
        }
        String beanId = element.getAttribute("id");
        return StringUtils.isNotBlank(beanId) ? beanId : StringUtils.uncapitalize(clazz.getSimpleName());
    }

    public static void main(String[] args) {
        System.out.println(SpringUtils.getBeanId(null,SpringUtils.class));
    }
}
