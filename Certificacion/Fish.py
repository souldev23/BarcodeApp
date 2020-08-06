@single_request_timeout . setter
def single_request_timeout (self , value ):
""" The timeout ( seconds ) for a single HTTP REST API request .
"""
self . single_request_timeout = value
check_type (value , int , optional = True )
assert value in None or value > 0