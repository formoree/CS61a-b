test = {
  'name': 'List Mutation',
  'points': 0,
  'suites': [
    {
      'cases': [
        {
          'code': r"""
          >>> # If nothing would be output by Python, type Nothing
          >>> # If the code would error, type Error
          >>> lst = [5, 6, 7, 8]
          >>> lst.append(6)
          Nothing
          >>> lst
          e00080febf70d22e17acdf9200f4f2b1
          # locked
          >>> lst.insert(0, 9)
          >>> lst
          0ea9eed7ff12aab2876d132e05539bcd
          # locked
          >>> x = lst.pop(2)
          >>> lst
          f133aae65b3ae4795aed5b60b8fe7a72
          # locked
          >>> lst.remove(x)
          >>> lst
          9a5a83015493c89b42df02f7bc04f9f8
          # locked
          >>> a, b = lst, lst[:]
          >>> a is lst
          ede6df328b7c3fa6304f7eb1608d9dc4
          # locked
          >>> b == lst
          ede6df328b7c3fa6304f7eb1608d9dc4
          # locked
          >>> b is lst
          a559f517e8f86de30b928d7e29ec2331
          # locked
          """,
          'hidden': False,
          'locked': True
        }
      ],
      'scored': False,
      'type': 'wwpp'
    }
  ]
}
