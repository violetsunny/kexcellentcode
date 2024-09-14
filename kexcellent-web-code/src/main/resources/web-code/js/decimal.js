function fDecimal(s, n) {
    s = parseFloat((s + "").replace(/[^\d.-]/g, "")).toFixed(n) + "";
    if (isNaN(s) || ((s + "").replace(/\s/g, "")) == "") {
        return "";
    }
    n = n > 0 && n <= 20 ? n : 2;
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
}