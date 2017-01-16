/* Generated By:JavaCC: Do not edit this line. MINIGrammar.java */
package parser;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import components.*;
import components.types.*;
import components.helpers.Position;
import components.interfaces.*;
import java.util.ArrayList;
import testsuite.MINIException;
import frontend.MINIParser;

@SuppressWarnings("unused")
public class MINIGrammar implements MINIGrammarConstants {
  private static File currentFile;
  private static HashMap<String, String> tokenTable = new HashMap<String, String>();
  private static Position generatePosition(Token t) {
        Position p = new Position(currentFile, t.beginLine);
        return p;
  }
  private static String tokenify(Token t) {
        String token = tokenTable.get(t.image);

        if (token == null) {
          token = t.image;
          if (token.charAt(0) == '"') {
                token = token.substring(1, token.length() - 1);
          }
          tokenTable.put(t.image, token);
        }
        return token;
  }
  public static FileNode parse(File in) throws MINIException
  {
    currentFile = in;
    CompositeType.clear();
    ArrayType.clear();
    try
    {
      MINIGrammar parser = new MINIGrammar(new FileInputStream(in));
      return parser.file();
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      return null;
    }
    catch (ParseException e)
    {
      MINIParser.handleParseError(in, e.currentToken, e.tokenImage, e.expectedTokenSequences);
      return null;
    }
    catch (TokenMgrError e) {
                MINIParser.handleTokenMgrError(in, e);
                return null;
    }
  }

  final public FileNode file() throws ParseException {
  FileNode f = new FileNode();
  ClassNode cls;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CLASS:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      cls = mini_class();
      f.classes.add(cls);
    }
    jj_consume_token(0);
    {if (true) return f;}
    throw new Error("Missing return statement in function");
  }

  final public ClassNode mini_class() throws ParseException {
  ClassNode cls;
  MemberNode classMember;
    jj_consume_token(CLASS);
    jj_consume_token(ID);
           cls = new ClassNode(tokenify(token), generatePosition(token));
    jj_consume_token(BRACE_OPEN);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      classMember = classMember();
      cls.addChild(classMember);
    }
    jj_consume_token(BRACE_CLOSE);
    {if (true) return cls;}
    throw new Error("Missing return statement in function");
  }

  final public MemberNode classMember() throws ParseException {
  MethodDeclarationNode m;
  Token memberName;
  TypeNode type;
    type = type();
    memberName = jj_consume_token(ID);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SEMICOLON:
      jj_consume_token(SEMICOLON);
      {if (true) return new FieldNode(tokenify(memberName), type, generatePosition(memberName));}
      break;
    case PARAN_OPEN:
      m = method(memberName, type);
      {if (true) return m;}
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public MethodDeclarationNode method(Token name, TypeNode type) throws ParseException {
  BlockNode body;
  ArrayList<NamedType> arguments;
    arguments = signature();
    body = blockStatement();
    {if (true) return new MethodDeclarationNode(tokenify(name), type, arguments, body, generatePosition(name));}
    throw new Error("Missing return statement in function");
  }

  final public TypeNode type() throws ParseException {
  Token type;
  int dimensions = 0;
    type = jj_consume_token(ID);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ARRAY_BEGIN:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      jj_consume_token(ARRAY_BEGIN);
      jj_consume_token(ARRAY_END);
                                                 dimensions++;
    }
    {if (true) return new TypeNode(tokenify(type), dimensions, generatePosition(type));}
    throw new Error("Missing return statement in function");
  }

  final public ArrayList < NamedType > signature() throws ParseException {
  ArrayList < NamedType > arguments = new ArrayList < NamedType > ();
  TypeNode type;
    jj_consume_token(PARAN_OPEN);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      type = type();
      jj_consume_token(ID);
      arguments.add(new NamedType(tokenify(token), type, generatePosition(token)));
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[4] = jj_gen;
          break label_4;
        }
        jj_consume_token(COMMA);
        type = type();
        jj_consume_token(ID);
        arguments.add(new NamedType(tokenify(token), type, generatePosition(token)));
      }
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
    jj_consume_token(PARAN_CLOSE);
    {if (true) return arguments;}
    throw new Error("Missing return statement in function");
  }

  final public StatementNode statement() throws ParseException {
  StatementNode s = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BRACE_OPEN:
      s = blockStatement();
      break;
    case IF:
      s = ifStatement();
      break;
    case WHILE:
      s = whileStatement();
      break;
    case RETURN:
      s = returnStatement();
      break;
    case PARAN_OPEN:
    case THIS:
    case NULL:
    case NEW:
    case MINUS:
    case NEGATION:
    case BOOL:
    case ID:
    case INT:
    case STRING:
      s = simpleStatement();
      break;
    case VAR:
      s = declaration();
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return s;}
    throw new Error("Missing return statement in function");
  }

  final public StatementNode simpleStatement() throws ParseException {
  ExpressionNode first, second;
  Token position;
    first = expression8();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SEMICOLON:
      position = jj_consume_token(SEMICOLON);
                             {if (true) return new SimpleStatementNode(generatePosition(position), first);}
      break;
    case ASSIGNMENT:
      position = jj_consume_token(ASSIGNMENT);
      second = expression8();
      jj_consume_token(SEMICOLON);
                                                                      {if (true) return new AssignmentStatementNode(first, generatePosition(position), second);}
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public DeclarationStatementNode declaration() throws ParseException {
  Token name;
  ExpressionNode expression;
    jj_consume_token(VAR);
    jj_consume_token(ID);
           name = token;
    jj_consume_token(ASSIGNMENT);
    expression = expression8();
    jj_consume_token(SEMICOLON);
    {if (true) return new DeclarationStatementNode(tokenify(name), expression, generatePosition(name));}
    throw new Error("Missing return statement in function");
  }

  final public BlockNode blockStatement() throws ParseException {
  BlockNode block;
  StatementNode statement;
    jj_consume_token(BRACE_OPEN);
                   block = new BlockNode(generatePosition(token));
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case BRACE_OPEN:
      case PARAN_OPEN:
      case THIS:
      case NULL:
      case NEW:
      case RETURN:
      case VAR:
      case IF:
      case WHILE:
      case MINUS:
      case NEGATION:
      case BOOL:
      case ID:
      case INT:
      case STRING:
        ;
        break;
      default:
        jj_la1[8] = jj_gen;
        break label_5;
      }
      statement = statement();
      block.children.add(statement);
    }
    jj_consume_token(BRACE_CLOSE);
    {if (true) return block;}
    throw new Error("Missing return statement in function");
  }

  final public IfNode ifStatement() throws ParseException {
  ExpressionNode condition;
  BlockNode first, second = null;
  Token position;
    position = jj_consume_token(IF);
    jj_consume_token(PARAN_OPEN);
    condition = expression8();
    jj_consume_token(PARAN_CLOSE);
    first = blockStatement();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ELSE:
      jj_consume_token(ELSE);
      second = blockStatement();
      break;
    default:
      jj_la1[9] = jj_gen;
      ;
    }
    {if (true) return new IfNode(generatePosition(position), condition, first, second);}
    throw new Error("Missing return statement in function");
  }

  final public WhileNode whileStatement() throws ParseException {
  ExpressionNode condition;
  BlockNode body;
  Token position;
    position = jj_consume_token(WHILE);
    jj_consume_token(PARAN_OPEN);
    condition = expression8();
    jj_consume_token(PARAN_CLOSE);
    body = blockStatement();
    {if (true) return new WhileNode(generatePosition(position), condition, body);}
    throw new Error("Missing return statement in function");
  }

  final public ReturnNode returnStatement() throws ParseException {
  ExpressionNode returnValue;
  Token position;
    position = jj_consume_token(RETURN);
    returnValue = expression8();
    jj_consume_token(SEMICOLON);
    {if (true) return new ReturnNode(generatePosition(position), returnValue);}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression8() throws ParseException {
  ExpressionNode condition;
  Token position;
  ExpressionNode t_branch;
  ExpressionNode f_branch;
    condition = expression7();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TERNARY_CONDITION:
      position = jj_consume_token(TERNARY_CONDITION);
      t_branch = expression8();
      jj_consume_token(TERNARY_DIVIDER);
      f_branch = expression8();
                               condition = new TernaryExpressionNode(generatePosition(position), condition, t_branch, f_branch);
      break;
    default:
      jj_la1[10] = jj_gen;
      ;
    }
    {if (true) return condition;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression7() throws ParseException {
  ExpressionNode current;
  ExpressionNode next;
  Token position;
  BinaryExpressionNode.Operator operator;
    current = expression6();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OR:
        ;
        break;
      default:
        jj_la1[11] = jj_gen;
        break label_6;
      }
      position = jj_consume_token(OR);
                      operator = BinaryExpressionNode.Operator.OR;
      next = expression6();
                           current = new BinaryExpressionNode(generatePosition(position), current, next, operator);
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression6() throws ParseException {
  ExpressionNode current;
  ExpressionNode next;
  Token position;
  BinaryExpressionNode.Operator operator;
    current = expression5();
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        ;
        break;
      default:
        jj_la1[12] = jj_gen;
        break label_7;
      }
      position = jj_consume_token(AND);
                       operator = BinaryExpressionNode.Operator.AND;
      next = expression5();
                           current = new BinaryExpressionNode(generatePosition(position), current, next, operator);
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression5() throws ParseException {
  ExpressionNode current;
  ExpressionNode next;
  Token position;
  BinaryExpressionNode.Operator operator;
    current = expression4();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EQUAL:
      case NOTEQUAL:
        ;
        break;
      default:
        jj_la1[13] = jj_gen;
        break label_8;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EQUAL:
        position = jj_consume_token(EQUAL);
                           operator = BinaryExpressionNode.Operator.SAME;
        break;
      case NOTEQUAL:
        position = jj_consume_token(NOTEQUAL);
                              operator = BinaryExpressionNode.Operator.NOTSAME;
        break;
      default:
        jj_la1[14] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      next = expression4();
      current= new BinaryExpressionNode(generatePosition(position), current, next, operator);
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression4() throws ParseException {
  ExpressionNode current;
  ExpressionNode next;
  Token position;
  BinaryExpressionNode.Operator operator;
    current = expression3();
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LESS_THAN_EQUAL:
      case GREATER_THAN_EQUAL:
      case LESS_THAN:
      case GREATER_THAN:
        ;
        break;
      default:
        jj_la1[15] = jj_gen;
        break label_9;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LESS_THAN_EQUAL:
        position = jj_consume_token(LESS_THAN_EQUAL);
                                     operator = BinaryExpressionNode.Operator.LTE;
        break;
      case GREATER_THAN_EQUAL:
        position = jj_consume_token(GREATER_THAN_EQUAL);
                                        operator = BinaryExpressionNode.Operator.GTE;
        break;
      case LESS_THAN:
        position = jj_consume_token(LESS_THAN);
                               operator = BinaryExpressionNode.Operator.LT;
        break;
      case GREATER_THAN:
        position = jj_consume_token(GREATER_THAN);
                                  operator = BinaryExpressionNode.Operator.GT;
        break;
      default:
        jj_la1[16] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      next = expression3();
          current = new BinaryExpressionNode(generatePosition(position), current, next, operator);
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression3() throws ParseException {
  ExpressionNode current;
  ExpressionNode next;
  Token position;
  BinaryExpressionNode.Operator operator;
    current = expression2();
    label_10:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MINUS:
      case PLUS:
        ;
        break;
      default:
        jj_la1[17] = jj_gen;
        break label_10;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
        position = jj_consume_token(PLUS);
                          operator = BinaryExpressionNode.Operator.PLUS;
        break;
      case MINUS:
        position = jj_consume_token(MINUS);
                           operator = BinaryExpressionNode.Operator.SUB;
        break;
      default:
        jj_la1[18] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      next = expression2();
          current = new BinaryExpressionNode(generatePosition(position), current, next, operator);
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression2() throws ParseException {
  ExpressionNode current;
  ExpressionNode next;
  Token position;
  BinaryExpressionNode.Operator operator;
    current = expression1();
    label_11:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULTIPLY:
      case DIVIDE:
      case REMAINDER:
        ;
        break;
      default:
        jj_la1[19] = jj_gen;
        break label_11;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case MULTIPLY:
        position = jj_consume_token(MULTIPLY);
                              operator = BinaryExpressionNode.Operator.MUL;
        break;
      case DIVIDE:
        position = jj_consume_token(DIVIDE);
                            operator = BinaryExpressionNode.Operator.DIV;
        break;
      case REMAINDER:
        position = jj_consume_token(REMAINDER);
                               operator = BinaryExpressionNode.Operator.MOD;
        break;
      default:
        jj_la1[20] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      next = expression1();
          current = new BinaryExpressionNode(generatePosition(position), current, next, operator);
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression1() throws ParseException {
  ExpressionNode current;
  Token position;
  UnaryExpressionNode.Operator operator;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MINUS:
    case NEGATION:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NEGATION:
        position = jj_consume_token(NEGATION);
                                operator = UnaryExpressionNode.Operator.NEGATION;
        break;
      case MINUS:
        position = jj_consume_token(MINUS);
                             operator = UnaryExpressionNode.Operator.MINUS;
        break;
      default:
        jj_la1[21] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      current = expression1();
        current = new UnaryExpressionNode(generatePosition(position), current, operator);
      break;
    case PARAN_OPEN:
    case THIS:
    case NULL:
    case NEW:
    case BOOL:
    case ID:
    case INT:
    case STRING:
      current = expression0();
      break;
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode expression0() throws ParseException {
  ExpressionNode base = null;
  ExpressionNode current;
  ArrayList<ExpressionNode> arguments = null;
  Token name;
    current = atomicExpression();
    label_12:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DOT:
        ;
        break;
      default:
        jj_la1[23] = jj_gen;
        break label_12;
      }
      jj_consume_token(DOT);
      name = jj_consume_token(ID);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PARAN_OPEN:
        arguments = argumentList();
        break;
      default:
        jj_la1[24] = jj_gen;
        ;
      }
          if (arguments != null) {
                current = new MemberExpressionNode(current, tokenify(name), arguments, generatePosition(name));
                arguments = null;
          } else {
                current = new MemberExpressionNode(current, tokenify(name), generatePosition(name));
          }
    }
    {if (true) return current;}
    throw new Error("Missing return statement in function");
  }

  final public ArrayList<ExpressionNode> argumentList() throws ParseException {
  ArrayList<ExpressionNode> arguments = new ArrayList<ExpressionNode>();
  ExpressionNode expr;
    jj_consume_token(PARAN_OPEN);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PARAN_OPEN:
    case THIS:
    case NULL:
    case NEW:
    case MINUS:
    case NEGATION:
    case BOOL:
    case ID:
    case INT:
    case STRING:
      expr = expression8();
      arguments.add(expr);
      label_13:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[25] = jj_gen;
          break label_13;
        }
        jj_consume_token(COMMA);
        expr = expression8();
        arguments.add(expr);
      }
      break;
    default:
      jj_la1[26] = jj_gen;
      ;
    }
    jj_consume_token(PARAN_CLOSE);
    {if (true) return arguments;}
    throw new Error("Missing return statement in function");
  }

  final public ExpressionNode atomicExpression() throws ParseException {
  ExpressionNode n;
  ExpressionNode expression;
  ArrayList<ExpressionNode> arguments = null;
  Token name;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ID:
      name = jj_consume_token(ID);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PARAN_OPEN:
        arguments = argumentList();
        break;
      default:
        jj_la1[27] = jj_gen;
        ;
      }
      // baseObject is null because it doesn't exist here
      if(arguments == null){
        {if (true) return new MemberExpressionNode(null, tokenify(name), generatePosition(name));}
          }
          {if (true) return new MemberExpressionNode(null, tokenify(name), arguments, generatePosition(name));}
      break;
    case THIS:
      jj_consume_token(THIS);
               {if (true) return new LiteralNode(tokenify(token), null, generatePosition(token));}
      break;
    case STRING:
      jj_consume_token(STRING);
                 {if (true) return new LiteralNode(tokenify(token), StringType.INSTANCE, generatePosition(token));}
      break;
    case INT:
      jj_consume_token(INT);
              {if (true) return new LiteralNode(tokenify(token), IntegerType.INSTANCE, generatePosition(token));}
      break;
    case BOOL:
      jj_consume_token(BOOL);
               {if (true) return new LiteralNode(tokenify(token), BooleanType.INSTANCE, generatePosition(token));}
      break;
    case NULL:
      n = nullExpression();
      break;
    case NEW:
      n = newExpression();
      break;
    case PARAN_OPEN:
      jj_consume_token(PARAN_OPEN);
      expression = expression8();
      jj_consume_token(PARAN_CLOSE);
                      expression.setParenthesis(); {if (true) return expression;}
      break;
    default:
      jj_la1[28] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  {if (true) return n;}
    throw new Error("Missing return statement in function");
  }

  final public NullExpressionNode nullExpression() throws ParseException {
  TypeNode type;
    jj_consume_token(NULL);
    jj_consume_token(LESS_THAN);
    type = type();
    jj_consume_token(GREATER_THAN);
    {if (true) return new NullExpressionNode(type);}
    throw new Error("Missing return statement in function");
  }

  final public NewExpressionNode newExpression() throws ParseException {
  NewExpressionNode n;
  TypeNode type;
    jj_consume_token(NEW);
    jj_consume_token(LESS_THAN);
    type = type();
                                        n = new NewExpressionNode(type);
    label_14:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[29] = jj_gen;
        break label_14;
      }
      jj_consume_token(COMMA);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INT:
        jj_consume_token(INT);
                n.arguments.add(new LiteralNode(tokenify(token), IntegerType.INSTANCE, generatePosition(token)));
        break;
      case ID:
        jj_consume_token(ID);
               n.arguments.add(new MemberExpressionNode(null, tokenify(token), generatePosition(token)));
        break;
      default:
        jj_la1[30] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    jj_consume_token(GREATER_THAN);
    {if (true) return n;}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public MINIGrammarTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[31];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x400,0x0,0x102000,0x8000,0x80000,0x0,0x6be22800,0x10100000,0x6be22800,0x4000000,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0xa0000000,0xa0000000,0x0,0x0,0x60000000,0x60622000,0x40000,0x2000,0x80000,0x60622000,0x2000,0x622000,0x80000,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x4000,0x0,0x0,0x0,0x4000,0x1e000,0x0,0x1e000,0x0,0x800,0x400,0x200,0x18,0x18,0x1e0,0x1e0,0x0,0x0,0x7,0x7,0x0,0x1e000,0x0,0x0,0x0,0x1e000,0x0,0x1e000,0x0,0xc000,};
   }

  /** Constructor with InputStream. */
  public MINIGrammar(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public MINIGrammar(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new MINIGrammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public MINIGrammar(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new MINIGrammarTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public MINIGrammar(MINIGrammarTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(MINIGrammarTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 31; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[50];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 31; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 50; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
