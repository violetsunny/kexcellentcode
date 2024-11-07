# 在一个数组中找另一个数组的匹配到的初始位置
## 朴素算法
就是暴力求解，最好理解

````java
class Solution {
    public int indexOf(int[] dist,int[] search){
        if(dist==null || search == null || dist.length==0 || search.length ==0 || search.length > dist.length){
            return -1;
        }
        int index = -1;
        int j = 0;
        for(int i=0;i<dist.length;i++){
            int x = i;//需要一个x下标帮助和j同时移动，但是如果不匹配还要回来重新开始
            while (j < search.length){
                if(dist[x]!=search[j]){
                    j=0;
                    break;
                }
                if(j==0){//记录初始匹配时的下标
                    index = i;
                }
                j++;
                x++;
            }

            if(j == search.length){//当全部匹配到才返回
                return index;
            }

        }

        return -1;
    }
}
````

## kmp算法
主打就是难理解，主要目的就是为了减少在匹配的时候已经知道的重复回退匹配问题。
其实就是回退的时候j没必要到0了，可以按照next数组知道有哪些已经匹配过了直接跳过从下一个开始，这样更快。

<pre>
KMP算法，对模式串的前后缀进行初始化next数组。然后进行匹配，减少回退指针。
next数组是通过模式串的公共前后缀串的长度来计算。
如abcdabd，求倒数第二b的next下标则是求abcda的公共前后缀长度，为1；最后一个d的next数组下标则是求abcdab的公共前后缀长度，为2。
如adadafade，求f的next下标则是求adada的公共前后缀长度，为3；倒数第三个a的next数组下标则是求adadaf的公共前后缀长度，为0。
</pre>
<pre>
KMP算法：
对模式串的前后缀进行初始化next数组。然后进行匹配，减少回退指针。O(km+kn)，模式串指的匹配字符串，不是要匹配的文本。例如，有一个文本 “abcdefghijklmn”，要在其中查找模式串 “defg”。这里的 “defg” 就是模式串。
一般的算法都需要指针回溯从头开始移动，但是KMP中通过next数组可以减少指针回退。
next数组是通过模式串的公共前后缀串的长度来计算。如abcdabd，求倒数第二b的next下标则是求abcda的公共前后缀长度，为1；最后一个d的next数组下标则是求abcdab的公共前后缀长度，为2。
公共前后缀串的意思next[i]是以i长度的字符串以包含前缀开始，和包含后缀结束的相同串的长度。例如"A D A D A F" next[5]下标数值是A开头和A结束开始匹配，可以得知有ADA字串为3。"A D A D A F A" next[6]下标数值是A开头和F结束开始匹配，可以得知F只有一个没有字串为0
j         0 1 2 3 4 5 6
模式      A B C D A B D
next[i]  -1 0 0 0 0 1 2
j         0 1 2 3 4 5 6 7 8
模式      A D A D A F A D E
next[i]  -1 0 0 1 2 3 0 1 2
通过next数组，我们可以知道当模式串和文本串匹配不上时就可以通过当前next[j]下标值知道需要跳过几个字符，而且文本的指针i不需要移动。
i 0 1 2 3 4 5 6 7 8 9 10
s A D A D A D A F A D E
p A D A D A F A D E
 -1 0 0 1 2 3 0 1 2
j->匹配不上，i,j=5,next[5]=3。就是跳过3个字符
i  0 1 2 3 4 5 6 7 8 9 10
s  A D A D A D A F A D E
p  A D A D A F A D E
当前i-j=2就是头是2位置，但是匹配从5开始继续匹配，都能匹配上。所以返回2
为啥？因为在next数组中我们已经找到了p公共子串，也就是当前位置前面的字符中公共前后缀。当前位置匹配不上，但是知道了前面公共子串的长度，我们就知道前面有3个长度是相同的，那就不需要再匹配了浪费时间直接回退到p[3]开始匹配即可。
那文本串为啥不要回退到开始位置？为啥文本可以从当前位置继续匹配，你看啊，s[5]和p[5]不匹配是不是也说明s[0-4]和p[0-4]能匹配上。p[0-4]也就是现在的next[5]计算过公共子串时匹配过，现在有公共子串说明前面有长度3相同字符，既可以继续匹配也就i=5当前位置，j=3位置开始，而且前面肯定不会有相同的因为在匹配公共子串时知道了有没有相同部分。如果现在是没有公共子串的，那说明前面这部分没有相同的了也不用回退，直接i=5当前位置，j从0开始匹配。

</pre>
````java
class Solution {
    /**
     * kmp O(n+m)
     * @param dist 目标数组
     * @param search 模式数组
     * @return
     */
    public int indexOf(int[] dist,int[] search){
        if(dist==null || search == null || dist.length==0 || search.length ==0 || search.length > dist.length){
            return -1;
        }

        //kmp算法初始化next数组
        int[] next = new int[search.length];
        getNext(search,next);

        int i = 0;
        int j = 0;
        int distSize = dist.length;
        int searchSize = search.length;

        while (i < distSize && j < searchSize) {
            if(j == -1 || dist[i] == search[j]){
                i++;
                j++;
            } else {
                j = next[j];
            }
        }

        if(j == searchSize){
            return i-j;
        }

        return -1;
    }

    /**
     * 获取next数组
     * @param search 模式数组
     * @param next next数组
     */
    private void getNext(int[] search , int[] next){
        int size = search.length-1;
        int i = 0;
        int j = -1;
        next[0] = -1;
        while (i < size) {
            //初始值和相同时加1
            if(j==-1 || search[i] == search[j]){
                i++;j++;
                if(search[i] != search[j]){
                    next[i] =j;
                } else {
                    next[i] = next[j];//相同就不用再比较
                }

            } else {
                j = next[j];
            }
        }
    }

}

````