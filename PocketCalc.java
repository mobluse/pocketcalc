/////////////////////////////////////////////////////////////////////
//
// Pocket Calculator emulates a pocket calculator of the simplest
// kind. It was Mikael O. Bonnier's first Java program.
//
/**
*    Pocket Calculator v1.1.1 by M.O.B. as Java SE applet and application. 
*    Copyright (C) 1995-2012 by Mikael O. Bonnier, Lund, Sweden.
*    License: GNU GPL v3 or later, http://www.gnu.org/licenses/gpl.txt
*    Donations are welcome to PayPal mikaelb@df.lth.se.
*    The source code is at http://www.df.lth.se/~mikaelb/PocketCalc/
*
*    @version        1.1.1
*    @author         Mikael O. Bonnier
*/
//
// It was developed using JDK-1.0 on Windows 95.
//
// Howto compile & run:
// 1. Install JDK-1.0 if not already done. You can download this
//    from http://java.sun.com in Developer's Corner [not valid 2012-Jan].
// 2. Save PocketCalc.java and a html-file with this tag:
//       <APPLET CODE="PocketCalc.class" WIDTH="395" HEIGHT="179">
//       <PARAM NAME="BGCOLOR" VALUE="#FF8000">
//       </APPLET>
//    in a file folder and at the command prompt enter:
// 3. javac PocketCalc.java
// 4. appletviewer pocketcalc.html
// 5. Now you may try out all the features of the calculator.
// 6. You may also run it as an application using: java PocketCalc
//
// Tips:
// *  Read the newsgroups comp.lang.java.*.
// *  The Java documentation is available in Windows Help format
//    from http://www.dippybird.com/ [not valid 2012-Jan].
//
// Thanks are due to many people for reporting bugs and suggestions,
// especially: Luis Fernandes, Bengt Ask, and Olivier Perrin.
//
// Revision history:
// 2012-Jan: 1.1.1  "private protected" replaced with  "private /* protected */".
//                  This change makes it work in OpenJDK/IcedTea.
// 1996-Nov: 1.1    Added parameter BGCOLOR. Added support to run
//                  as an application.
// 1996-Jul: 1.01   Fixed bugs of unary operations after equals
//                  operations.
// 1996-Jan: 1.0    All keys implemented and an X Windows problem
//                  fixed. 
// 1995-Dec: 1.0bX  Beta versions.
//
// Suggestions, improvements, and bug-reports
// are always welcome to:
//                  Mikael Bonnier
//                  Osten Undens gata 88
//                  SE-227 62  LUND
//                  SWEDEN
//
// Or use my internet addresses:
//                  mikaelb@df.lth.se
//                  http://www.df.lth.se/~mikaelb/
//              _____
//             /   / \
// ***********/   /   \***********
//           /   /     \
// *********/   /       \*********
//         /   /   / \   \
// *******/   /   /   \   \*******
//       /   /   / \   \   \
// *****/   /   /***\   \   \*****
//     /   /__ /_____\   \   \
// ***/               \   \   \***
//   /_________________\   \   \
// **\                      \  /**
//    \______________________\/
//
// Mikael Bonnier
/////////////////////////////////////////////////////////////////////

import java.awt.*;
import java.applet.Applet;

public class PocketCalc extends Applet 
{
   TextField txtDisp;
   public final int OP_NONE = 0;
   public final int OP_ADD = 1;
   public final int OP_SUB = 2;
   public final int OP_MUL = 3;
   public final int OP_DIV = 4;
   public final int OP_NEG = 5;
   public final int OP_SQRT = 6;
   public final int OP_EQ = 7;
   public final int OP_C = 8;
   public final int OP_AC = 9;
   public final int OP_MC = 10;
   public final int OP_MR = 11;
   public final int OP_MM = 12;
   public final int OP_MP = 13;
   public final int OP_PCT = 14;
   public final int DECSEP = -1;

   String msDecimal;
   int mnOp = OP_NONE;
   boolean mbNewNumber = true;
   boolean mbDecimal = false;
   double mdReg = 0.0;
   double mdMemory = 0.0;
   boolean mbConstant = false;
   double mdConstant = 0.0;
   int mnConstantOp = OP_NONE;
   boolean mbPercent = false;
   double mdFirst = 0.0;

   public void init() 
   {
      CalcButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
      CalcButton btnDecSep, btnNeg, btnSqrt, btnPlus, btnMinus;
      CalcButton btnTimes, btnDiv, btnEqual, btnClear, btnAllClear;
      CalcButton btnMemoryClear, btnMemoryRecall, btnMemoryMinus,
         btnMemoryPlus;
      CalcButton btnPercent;

      setLayout(null);
      setFont(new Font("Helvetica", Font.PLAIN, 14));
      int nBGColor;
      try
      {
         nBGColor = Integer.parseInt(getParameter("bgcolor").substring(1), 16); // Skip #
      }
      catch(RuntimeException e)
      {
         nBGColor = 0xFF8000;
      }
      setBackground(new Color(nBGColor));

      btn0 = new CalcButton("0", OP_NONE, 0);
      add(btn0);
      btn0.reshape(64, 144, 96, 24);

      btn1 = new CalcButton("1", OP_NONE, 1);
      add(btn1);
      btn1.reshape(64, 112, 40, 24);

      btn2 = new CalcButton("2", OP_NONE, 2);
      add(btn2);
      btn2.reshape(120, 112, 40, 24);

      btn3 = new CalcButton("3", OP_NONE, 3);
      add(btn3);
      btn3.reshape(176, 112, 40, 24);

      btn4 = new CalcButton("4", OP_NONE, 4);
      add(btn4);
      btn4.reshape(64, 80, 40, 24);

      btn5 = new CalcButton("5", OP_NONE, 5);
      add(btn5);
      btn5.reshape(120, 80, 40, 24);

      btn6 = new CalcButton("6", OP_NONE, 6);
      add(btn6);
      btn6.reshape(176, 80, 40, 24);

      btn7 = new CalcButton("7", OP_NONE, 7);
      add(btn7);
      btn7.reshape(64, 48, 40, 24);

      btn8 = new CalcButton("8", OP_NONE, 8);
      add(btn8);
      btn8.reshape(120, 48, 40, 24);

      btn9 = new CalcButton("9", OP_NONE, 9);
      add(btn9);
      btn9.reshape(176, 48, 40, 24);	

      btnDecSep = new CalcButton("·", OP_NONE, DECSEP);
      add(btnDecSep);
      btnDecSep.reshape(176, 144, 40, 24);

      btnNeg = new CalcButton("+/-", OP_NEG, 0);
      add(btnNeg);
      btnNeg.reshape(8, 48, 40, 24);

      btnSqrt = new CalcButton("Sqrt", OP_SQRT, 0);
      add(btnSqrt);
      btnSqrt.reshape(8, 80, 40, 24);

      btnPlus = new CalcButton("+", OP_ADD, 0);
      add(btnPlus);
      btnPlus.reshape(232, 112, 40, 56);

      btnMinus = new CalcButton("-", OP_SUB, 0);
      add(btnMinus);
      btnMinus.reshape(288, 112, 40, 24);

      btnTimes = new CalcButton("×", OP_MUL, 0);
      add(btnTimes);
      btnTimes.reshape(232, 80, 40, 24);

      btnDiv = new CalcButton("÷", OP_DIV, 0);
      add(btnDiv);
      btnDiv.reshape(288, 80, 40, 24);

      btnEqual = new CalcButton("=", OP_EQ, 0);
      add(btnEqual);
      btnEqual.reshape(288, 144, 40, 24);

      btnClear = new CalcButton("C", OP_C, 0);
      add(btnClear);
      btnClear.reshape(8, 112, 40, 24);

      btnAllClear = new CalcButton("AC", OP_AC, 0);
      add(btnAllClear);
      btnAllClear.reshape(8, 144, 40, 24);

      btnMemoryClear = new CalcButton("MC", OP_MC, 0);
      add(btnMemoryClear);
      btnMemoryClear.reshape(344, 48, 40, 24);

      btnMemoryRecall = new CalcButton("MR", OP_MR, 0);
      add(btnMemoryRecall);
      btnMemoryRecall.reshape(344, 80, 40, 24);

      btnMemoryMinus = new CalcButton("M-", OP_MM, 0);
      add(btnMemoryMinus);
      btnMemoryMinus.reshape(344, 112, 40, 24);

      btnMemoryPlus = new CalcButton("M+", OP_MP, 0);
      add(btnMemoryPlus);
      btnMemoryPlus.reshape(344, 144, 40, 24);

      btnPercent = new CalcButton("%", OP_PCT, 0);
      add(btnPercent);
      btnPercent.reshape(232, 48, 40, 24);

      txtDisp = new TextField("0", 80);
      txtDisp.setEditable(false);
      add(txtDisp);
      txtDisp.reshape(64, 8, 268, 31);

      validate();

      String sOneTenth = (new Double(0.1)).toString();
      msDecimal = sOneTenth.substring(sOneTenth.length()-2).substring(0, 1);
         // Handles language dependent decimal separator.
   }

   public void append(int nValue) 
   {
      String sDigit;

      if(nValue == DECSEP)
         if(!mbDecimal)
         {
            if(mbNewNumber)
            {
               txtDisp.setText("0");
               mbNewNumber = false;
            }
            mbDecimal = true;
            sDigit = msDecimal;
         }
         else
            return; 
      else
         sDigit = (new Integer(nValue)).toString();
      if(mbNewNumber)
      {
         txtDisp.setText(sDigit);
         if(nValue != 0)
            mbNewNumber = false;
      }
      else
         txtDisp.setText(txtDisp.getText() + sDigit);
      repaint();
   }

   public void doOp(int nNewOp)
   {
      double dDisp;

      dDisp = (new Double(txtDisp.getText())).doubleValue();
      if(mbPercent)
         if(nNewOp != OP_ADD && nNewOp != OP_SUB)
            mbPercent = false;
      if(!mbPercent)
         switch(nNewOp)
         {
         case OP_ADD:
         case OP_SUB:
         case OP_MUL:
         case OP_DIV:
            if(mbNewNumber)
            {
               if(nNewOp == mnOp && !mbConstant)
               {
                  mbConstant = true;
                  mdConstant = dDisp;
                  mnConstantOp = nNewOp;
               }
               else
                  mbConstant = false;
            }
            else
               mbConstant = false;
         case OP_EQ:
         case OP_MM:
         case OP_MP:
         case OP_PCT:
            if(!mbNewNumber || isEqOp(nNewOp))
            {
               if(mbConstant)
               {
                  mdReg = mdConstant;
                  mnOp = mnConstantOp;
               }
               mbPercent = nNewOp == OP_PCT;
               if(mbPercent)
                  mdFirst = mdReg;
               switch(mnOp)
               {
               case OP_ADD:
                  mdReg = mdReg + dDisp;
                  break;
               case OP_SUB:
                  mdReg = mdReg - dDisp;
                  break;
               case OP_MUL:
                  mdReg = mdReg * dDisp;
                  break;
               case OP_DIV:
                  mdReg = mdReg / dDisp;
                  break;
               case OP_EQ:
               case OP_MM:
               case OP_MP:
               case OP_PCT:
               case OP_NONE:
                  mdReg = dDisp;
                  break;
               }
               if(mbPercent)
                  mdReg /= 100;
               txtDisp.setText((new Double(mdReg)).toString());
            }
            mnOp = nNewOp;
            mbNewNumber = true;
            mbDecimal = false;
            break;
         }
      switch(nNewOp)
      {
      case OP_ADD:
         if(mbPercent)
         {
            mdReg = mdFirst + mdReg;
            txtDisp.setText((new Double(mdReg)).toString());
            mbPercent = false;
         }
         break;
      case OP_SUB:
         if(mbPercent)
         {
            mdReg = mdFirst - mdReg;
            txtDisp.setText((new Double(mdReg)).toString());
            mbPercent = false;
         }
         break;
      case OP_NEG:
         dDisp = -dDisp;
         txtDisp.setText((new Double(dDisp)).toString());
         if(isEqOp(mnOp))
         {
            mdReg = dDisp;
         }
         break;
      case OP_SQRT:
         dDisp = Math.sqrt(dDisp);
         txtDisp.setText((new Double(dDisp)).toString());
         if(isEqOp(mnOp))
         {
            mdReg = dDisp;
         }
         mbNewNumber = true;
         mbDecimal = false;
         break;
      case OP_C:
         dDisp = 0.0;
         txtDisp.setText("0");
         if(isEqOp(mnOp))
         {
            mdReg = dDisp;
         }
         mbNewNumber = true;
         mbDecimal = false;
         break;
      case OP_AC:
         txtDisp.setText("0");
         mnOp = OP_NONE;
         mbNewNumber = true;
         mbDecimal = false;
         mdReg = 0.0;
         mbConstant = false;
         break;
      case OP_MC:
         mdMemory = 0.0;
         break;
      case OP_MR:
         dDisp = mdMemory;
         txtDisp.setText((new Double(dDisp)).toString());
         if(isEqOp(mnOp))
         {
            mdReg = dDisp;
         }
         mbNewNumber = true;
         mbDecimal = false;
         break;
      case OP_MM:
         mdMemory -= mdReg;
         break;
      case OP_MP:
         mdMemory += mdReg;
         break;
      }
   }

   private /* protected */ boolean isEqOp(int nOp)
   {
      return nOp == OP_EQ || nOp == OP_MM 
         || nOp == OP_MP || nOp == OP_PCT;
   }

   public String[][] getParameterInfo()
   {
      String pinfo[][] = {
         {"bgcolor", "#RRGGBB", "Background color"}
      };
      return pinfo;
   }

   public static void main(String args[]) 
   {
      CalcFrame frm = new CalcFrame("Pocket Calculator");
      PocketCalc pnl = new PocketCalc();
      pnl.init();
      frm.add("Center", pnl);
      frm.pack();
      frm.resize(401, 205);
      frm.show();
   }
}

class CalcButton extends Button
{
   int mnOp;
   int mnValue;

   CalcButton(String sText, int nOp, int nValue)
   {
      super(sText);
      mnOp = nOp;
      mnValue = nValue;   
   }

   public boolean action(Event evt, Object arg) 
   {
      PocketCalc par = (PocketCalc)getParent();

      if(mnOp == par.OP_NONE)
         par.append(mnValue);
      else
         par.doOp(mnOp);
      return true;
   }
}

class CalcFrame extends Frame
{
   CalcFrame(String s)
   {
      super(s);
   }

   public boolean handleEvent(Event evt) 
   {
      if(evt.id == Event.WINDOW_DESTROY) 
      { 
         System.exit(0);
         return true;
      } 
      else 
         return super.handleEvent(evt);
   }
}
