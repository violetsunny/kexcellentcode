/**
 * LY.com Inc.
 * Copyright (c) 2004-2024 All Rights Reserved.
 */
package top.kexcellent.back.code.algorithm;

import java.util.*;

/**
 * 单词拆分
 * @author kanglele
 * @version $Id: T2, v 0.1 2024/11/5 17:37 kanglele Exp $
 */
public class WordBreak {
    List<String> reslist = new ArrayList<>();
    public List<String> wordBreak2(String s, List<String> wordDict) {
        StringBuilder sb = new StringBuilder();
        dfs(s,wordDict,sb);
        return reslist;
    }

    private void dfs(String s,List<String> wordDict,StringBuilder sb){
        if(s.isEmpty()){
            reslist.add(sb.substring(1));
            return;
        }

        for(String word:wordDict){
            if(s.startsWith(word)){
                StringBuilder temp = new StringBuilder(sb).append(" ").append(word);
                dfs(s.substring(word.length()),wordDict,temp);
            }
        }
    }

    public static void main(String[] args) {
        WordBreak t2 = new WordBreak();
        List<String> list = t2.wordBreak2("pineapplepenapple",new ArrayList<String>(){{add("apple");add("pen");add("applepen");add("pine");add("pineapple");}});
        System.out.println(Arrays.toString(list.toArray()));
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()+1];
        dp[0] = true;
        for(int i=1;i<=s.length();i++){
            for(int j=0;j<i;j++){
                if(dp[j] && wordDict.contains(s.substring(j,i))){//dp[j]变相从下一个j开始截取字符匹配
                    dp[i]=true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
