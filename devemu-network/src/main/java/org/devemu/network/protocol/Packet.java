package org.devemu.network.protocol;

import com.google.common.collect.Lists;
import org.devemu.network.client.SimpleClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Packet {
	private String identificator = "";//2 letters
	private String firstParam = "";//After identificator and before the first '|'
	private List<String> param = new ArrayList<String>();//Param split by '|'
	private static String[] HEX_CHARS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    public static final class PacketFactory {
        private final Packet result = new Packet();
        private final List<String> params = Lists.newArrayList();

        public PacketFactory identificator(String identificator) {
            result.setIdentificator(identificator);
            return this;
        }

        public PacketFactory firstParam(Object firstParam) {
            result.setFirstParam(firstParam.toString());
            return this;
        }

        public PacketFactory param(Object param) {
            params.add(param.toString());
            return this;
        }

        public PacketFactory params(Collection<String> params) {
            this.params.addAll(params);
            return this;
        }

        public PacketFactory params(Object param1, Object param2, Object... params) {
            this.params.add(param1.toString());
            this.params.add(param2.toString());
            for (Object param : params) {
                this.params.add(param.toString());
            }
            return this;
        }

        public Packet create() {
            result.setParam(params);
            return result;
        }
    }

    public static PacketFactory factory() {
        return new PacketFactory();
    }

    public static PacketFactory packet() { // convenient method for static import
        return factory();
    }
	
	public static Packet decomp(String arg1) {
		Packet loc1 = new Packet();
		/*int loc3 = Integer.parseInt(arg1.substring(0, 2).toUpperCase(), 16) * 2;
		String loc5 =  decrypt(arg1.substring(2),arg2.getHash().getKey(),loc3);
		System.out.println(loc5);*/
		loc1.setIdentificator(arg1.substring(0, 2));
		if(arg1.contains("|")) {
			String[] loc4 = arg1.split("|");
			if(loc4[0].length() > 2)
				loc1.setFirstParam(loc4[0].substring(2));
			String[] loc2 = Arrays.copyOfRange(loc4, 1, loc4.length);
			loc1.setParam(Arrays.asList(loc2));
		}else if(arg1.length() > 2) {
			loc1.setFirstParam(arg1.substring(2));
		}
		return loc1;
	}
	
	public static String decompToString(String arg1,SimpleClient arg2) {
		int loc3 = Integer.parseInt(arg1.substring(0, 2).toUpperCase(), 16) * 2;
		return decrypt(arg1.substring(2),arg2.getHash().getKey(),loc3);
	}
	
	public static String crypt(String arg1, String arg2,int arg3) {
		String loc1 = "";
		int loc2 = arg2.length();
		String loc3 = preEscape(arg1);
		for(int i = 0; i < loc3.length(); i++) {
			loc1 += d2h(loc3.codePointAt(i) ^ arg2.charAt((i + arg3) % loc2));
		}
		return loc1;
	}
	
	public static String d2h(int arg1) {
		int loc2 = arg1;
		if(arg1 > 255) {
			loc2 = 255;
		}
		return (HEX_CHARS[(int) Math.floor(loc2 / HEX_CHARS.length)] + HEX_CHARS[loc2 % HEX_CHARS.length]);
	}
	
	public static String preEscape(String arg1) {
		String loc1 = "";
		for(int i = 0; i < arg1.length(); i++) {
			char loc5 = arg1.charAt(i);
			int loc6 = arg1.codePointAt(i);
			if(loc6 < 32 || (loc6 > 127 || (loc5 == '%' || loc5 == '+'))) {
				//loc1 += HtmlEscapers.
				continue;
			}
			loc1 += loc5;
		}
		return loc1;
	}
	
	public static String checksum(String arg1) {
		int loc2 = 0;
		for(int i = 0; i < arg1.length(); i++) {
			loc2 += (arg1.codePointAt(i) % HEX_CHARS.length);
		}
		return HEX_CHARS[loc2 % HEX_CHARS.length];
	}
	
	public static String decrypt(String arg1, String arg2,int arg3) {
		String loc1 = "";
		int loc2 = arg2.length();
		int loc3 = 0;
		for(int i = 0; i < (arg1.length()); i+=2) {
			loc1 += ((char)(Integer.parseInt(arg1.substring(i, i + 2), 16) ^ arg2.charAt((loc3++ + arg3) % loc2)));
		}
		return loc1;
	}

	public String toString(SimpleClient arg1) {
		String loc1 = getIdentificator() + getFirstParam();
		for(String loc2 : getParam()) {
			loc1 += ("|" + loc2);
		}
		String loc3 = HEX_CHARS[arg1.getHash().getId()];
		String loc4 = checksum(loc1);
		loc3 += loc4;
		int loc5 = Integer.parseInt(loc4, 16) * 2;
		return (loc3 + crypt(loc1,arg1.getHash().getKey(),loc5));
	}
	
	@Override
	public String toString() {
		String loc1 = getIdentificator() + getFirstParam();
		for(String loc2 : getParam()) {
			loc1 += ("|" + loc2);
		}
		return loc1;
	}
	
	public String getIdentificator() {
		return identificator;
	}

	public void setIdentificator(String identificator) {
		this.identificator = identificator;
	}

	public String getFirstParam() {
		return firstParam;
	}

	public void setFirstParam(String firstParam) {
		this.firstParam = firstParam;
	}

	public List<String> getParam() {
		return param;
	}

	public void setParam(List<String> param) {
		this.param = param;
	}
}
