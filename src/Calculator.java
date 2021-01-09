import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.lang.Math.*;

public class Calculator extends JFrame implements ActionListener{
	ArrayList<String> op = new ArrayList<>();
	ArrayList<String> num = new ArrayList<>();
	ArrayList<Double> num2 = new ArrayList<>();
	JTextField jf = new JTextField("0");
	String[] msg = {"1","2","3","+","4","5","6","-","7","8","9","*","0",".","√","/"};
	JButton[] j = new JButton[msg.length]; //버튼 배열 생성 -> 배경색과 텍스트를 초기화시켜주기 위해서
	JButton[] jj = new JButton[2];
	double result;
	
	Calculator(){
		JPanel p = new JPanel(); // 컨ㅌㅔ이너 대표적
		p.setLayout(new BorderLayout());
		JPanel pp = new JPanel();
		pp.setLayout(new GridLayout(4,4,10,10));
		
		
		for(int i = 0; i < msg.length; i ++) {
			j[i] = new JButton(msg[i]); //버튼 생성 없으면 Nullpointerexception
			j[i].setBackground(Color.lightGray);
			if(msg[i].equals("+")||msg[i].equals("-")||msg[i].equals("*")||msg[i].equals("/")) {
				j[i].setBackground(Color.white);
			}
		}
		
		setTitle("인혜의 계산기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jj[0] = new JButton("Clear"); // Clear버튼 생성
		jj[1] = new JButton("Enter"); // Enter 버튼 생성
		jj[0].setBackground(Color.WHITE);
		jj[1].setBackground(Color.white);
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());//컨테이너에 들어오는 컴포넌트들의 배치를 위해
		c.add(jf,BorderLayout.NORTH);
		c.add(jj[1],BorderLayout.SOUTH);;
		c.add(p,BorderLayout.CENTER); //컨테이너 위에 컨테이너를 올림
		p.add(jj[0],BorderLayout.NORTH);
		
		p.add(pp,BorderLayout.CENTER);
		
		for(int i = 0; i < msg.length; i ++) {
			pp.add(j[i]);
			j[i].addActionListener(this); // calculator를 받음
		}
		
		jj[0].addActionListener(this);
		jj[1].addActionListener(this);
		
		
		setSize(400,400);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Calculator c = new Calculator();

	}

	@Override
	public void actionPerformed(ActionEvent e) { // 버튼 이벤트 실행 메소드
		JButton btn = (JButton) e.getSource();
		String op = btn.getText();
		String str;
		
		boolean n = btn.getText().equals("+") || btn.getText().equals("-"); // 연산자가 입력됐는지 확인하기 위한 변수
		boolean n2 = btn.getText().equals("/") || btn.getText().equals("*");
		
		if(op.equals("Enter")) { // 입력받은 값이 enter일 경우, 전에 계산된 값과 현재 입력된 값을 계산
			str = "";
			for(int i = 0;i < num.size();i++) {
				str += num.get(i);
			}
			cal(this.op.get(0),Double.parseDouble(str)); //아직 num2에 넘어가지 않았으므로 직접 문자열에서 실수로 형변환해줘야 함
			jf.setText(Double.toString(result));
			reset(); // 총 계산을 보여주고 모두 리셋
		}else if(op.equals("Clear")) { // clear일 경우 모두 초기화
			reset();
			jf.setText("0");
		}else if(op.equals("√")) { //루트를 숫자 입력 후 누르면 루트를 씌워준 값을 결과값에 대입
			
			try {
				str = "";
				for(int i = 0;i < num.size();i++) {
					str += num.get(i);
				}
				this.result = Math.sqrt(Double.parseDouble(str));
				System.out.println(result);
				jf.setText(Double.toString(result));
				num2.add(result); //결과값을 연산하기 위해 num2에 추가
				
			}catch(Exception e5) {
				jf.setText("숫자를 입력해주세요.");
			}
		
		}
		else {
	
		if(n == false && n2 == false) { // 연산자가 입력이 안됐을 때 실행
			num.add(op);
			str = "";
			for(int i = 0;i < num.size();i++) {
				str += num.get(i);
			}
			jf.setText(str);
		}else if(n == true || n2 == true) { // 연산자가 입력되었을 때 이전의 문자열을 숫자로 만들어 ArrayList에 대입
			this.op.add(op);
			
			str = "";
			try { // 숫자를 입력안하고 처음부터 연산자를 눌렀을 경우 예외처리해줌
			for(int i = 0;i < num.size();i++) { // num2에 실수로 형변환해서 저장하기 위한 작업
				str += num.get(i);
			}
			   num2.add(Double.parseDouble(str)); //쌓인 숫자를 형변환
			
			while(num.size() != 0) { // 쌓였던 숫자들을 삭제
				int i;
				i = num.size();
				num.remove(i-1);
				i--;
			}
			}catch(NumberFormatException e1) {
				jf.setText("숫자를 입력해주세요.");
			}
			
			if(num2.size()==2) {
				cal(this.op.get(0),num2.get(1));
				
				while(num2.size() != 0) { // 쌓였던 숫자들을 삭제
					int i;
					i = num2.size();
					num2.remove(i-1);
					i--;
				}
				
				this.op.remove(0);
				num2.add(result);

				}
			
			
			}
		}
		
		
		
	}
	
	private void cal(String op,double a) {
		
		if(op.equals("+")) {
			this.result =  num2.get(0) + a;
		}else if(op.equals("-")) {
			this.result = num2.get(0) - a;
		}else if(op.equals("/")) {
			this.result = num2.get(0)/a;
		}else if(op.equals("*")) {
			this.result = num2.get(0)*a;
		}
		


	}
	
	private void reset() {
		while(num.size() != 0) {
			int i;
			i = num.size();
			num.remove(i-1);
			i--;
		}
		
		while(num2.size() != 0) {
			int i;
			i = num2.size();
			num2.remove(i-1);
			i--;
		}
		
		while(this.op.size() != 0) {
			int i;
			i = op.size();
			op.remove(i-1);
			i--;
		}
		
	}
}
