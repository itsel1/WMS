package com.example.temp.trans.cj;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CJTerminalVo {
	
	public CJTerminalVo() {
		dlvClsfCd =""; 
		dlvSubClsfCd ="";
		dlvPreArrBranShortNm ="";
		dlvPreArrEmpNickNm =""; 
		dlvPreArrEmpNm ="";
		rcvrClsfAddr ="";
		rcvrShortAddr ="";
		rcvrEtcAddr ="";
		errorCd ="";
		errorMsg ="";
	}
    private String dlvClsfCd ; 
    private String dlvSubClsfCd ; 
    private String dlvPreArrBranShortNm ;
    private String dlvPreArrEmpNickNm;
    private String dlvPreArrEmpNm ;
    private String rcvrClsfAddr ;
    private String rcvrShortAddr;
    private String rcvrEtcAddr;
    private String errorCd;
    private String errorMsg;
}
